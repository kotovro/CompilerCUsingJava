package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Function;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.*;
import java.lang.StringBuilder;

public class FuncCallNode extends BasicNode implements ExprNode, StmtNode {
    private IdentNode functionName = null;
    private List<ExprNode> params = null;
    Function func = null;
    private Scope scope;
    private Type returnType;

    public FuncCallNode(IdentNode functionName, Collection<ExprNode> params) {
        this.functionName = functionName;
        if (params != null) {
            this.params = new ArrayList<>();
            this.params.addAll(params);
        }
    }

    @Override
    public Collection<? extends AstNode> childs() {
        List<AstNode> children = new ArrayList<>();
        children.add(functionName);
        children.addAll(params);
        return children;
    }

    @Override
    public String toString() {
        return functionName.toString() + "()";
    }

    public List<ExprNode> getParams() {
        return Collections.unmodifiableList(params);
    }

    @Override
    public void semanticCheck() {
        functionName.semanticCheck();
        for (ExprNode param : params) {
            param.semanticCheck();
        }
        try {
            func = scope.getFunction(functionName.getName(), getParamTypes());
        } catch (SemanticException e) {
            throw new SemanticException("Function '" + functionName.getName() + "' is not defined: " + e.getMessage());
        }
        
        for (int i = 0; i < params.size(); i++) {
            if (params.get(i).getType() != func.getParameters().get(i)) {
                params.set(i, new CastNode(func.getParameters().get(i), params.get(i), scope));
            }
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        functionName.initialize(scope);
        for (ExprNode param : params) {
            param.initialize(scope);
        }
    }

    @Override
    public Type getType() {
        if (func == null) {
            ArrayList<Type> types = new ArrayList<>();
            for (ExprNode arg : params) {
                types.add(arg.getType());
            }
            func = scope.getFunction(functionName.getName(), types);
        }
        return func.getReturnType();
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
    }

    private List<Type> getParamTypes() {
        List<Type> types = new ArrayList<>();
        for (ExprNode param : params) {
            types.add(param.getType());
        }
        return types;
    }

    @Override
    public StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();
        params.forEach(e -> code.append(e.generateCode()));
        code.append("invokestatic ").append(func.getName()).append("(");
        func.getParameters().forEach(e -> code.append(e.toString()));
        code.append(")").append(func.getReturnType().toString()).append("\n");

        return code;
    }
}