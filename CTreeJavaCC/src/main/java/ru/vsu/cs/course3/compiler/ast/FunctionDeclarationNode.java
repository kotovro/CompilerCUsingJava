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
//        List<BasicNode> astNodes = new ArrayList<>();
//        List<BasicNode> groupNodes = new ArrayList<>();
//        groupNodes.add(this.name);
//        astNodes.add(new GroupNode(this.type.toString(), groupNodes));
//        astNodes.add(new GroupNode("params", new ArrayList<>(this.params)));
//        astNodes.add(this.body);
//        return astNodes;
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
        
        // Create new scope for function body
        LocalScope functionScope = new LocalScope(scope);
        this.scope = functionScope;
        
        // Add function to parent scope
        scope.addFunction(function);
        
        // Set current function in the global scope
        if (scope instanceof GlobalScope) {
            ((GlobalScope) scope).setCurrentFunction(function);
        }

        // Initialize parameters in the function scope
        for (FunctionParamDeclarationNode param : params) {
            param.initialize(functionScope);
        }

        // Initialize body with the function scope
        body.initialize(functionScope);
    }


    @Override
    public void printTree(PrintStream printStream) {

    }
}