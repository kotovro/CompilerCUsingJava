package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Function;
import ru.vsu.cs.course3.compiler.semantic.LocalScope;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.GlobalScope;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FunctionDeclarationNode extends BasicNode implements StmtNode {
    private TypeNode type;
    private IdentNode name;
    private List<FunctionParamDeclarationNode> params;
    private StmtNode body;

    public FunctionDeclarationNode(TypeNode type, IdentNode name, List<FunctionParamDeclarationNode> params, StmtNode body) {
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public String toString() {
        return "function " + this.name ;
    }

    @Override
    public List<AstNode> childs() {
        return Collections.singletonList(body);
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    public IdentNode getName() {
        return name;
    }

    public void setName(IdentNode name) {
        this.name = name;
    }

    public List<FunctionParamDeclarationNode> getParams() {
        return params;
    }

    public void setParams(List<FunctionParamDeclarationNode> params) {
        this.params = params;
    }

    public StmtNode getBody() {
        return body;
    }

    public void setBody(StmtNode body) {
        this.body = body;
    }

    @Override
    public void semanticCheck() {
        body.semanticCheck();
    }

    @Override
    public void initialize(Scope scope) {
        ArrayList<Type> parameters = new ArrayList<>();
        for (FunctionParamDeclarationNode functionParam: params) {
            parameters.add(Type.fromString(functionParam.getType().toString()));
        }
        
        Function function = new Function(name.getName(), parameters, Type.fromString(type.getName()));
        
        LocalScope functionScope = new LocalScope(scope);
        this.scope = functionScope;
        
        scope.addFunction(function);
        
        if (scope instanceof GlobalScope) {
            ((GlobalScope) scope).setCurrentFunction(function);
        }

        for (FunctionParamDeclarationNode param : params) {
            param.initialize(functionScope);
        }

        body.initialize(functionScope);
    }



    private List<Type> getParamTypes() {
        List<Type> types = new ArrayList<>();
        if (params != null) {
            for (FunctionParamDeclarationNode param : params) {
                types.add(Type.fromString(param.getType().getName()));
            }
        }
        return types;
    }

    @Override
    public StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();
        Type returnType = Type.fromString(this.type.getName());
        code.append(".method public static ").append(name.getName()).append("(");
        if (params != null) {
            for (FunctionParamDeclarationNode param : params) {
                code.append(Type.fromString(param.getType().getName()).getAbbreviation());
            }
        }
        code.append(")").append(returnType.getAbbreviation()).append("\n");
        code.append(".limit stack 20\n"); // TODO: Calculate proper stack limit
        code.append(".limit locals ").append(((LocalScope)scope).variables.size()).append("\n"); // Assuming params are also local vars
        code.append(body.generateCode());

        // Handle return based on return type
        if (returnType != Type.VOID) {
             // Assuming the return value is on top of the stack after body execution
             code.append(returnType.getCommandWordPrefix()).append("return\n");
        } else {
             code.append("return\n");
        }

        code.append(".end method\n\n");
        return code;
    }
}