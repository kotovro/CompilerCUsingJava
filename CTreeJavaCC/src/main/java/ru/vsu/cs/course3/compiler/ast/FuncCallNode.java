package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Function;
import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.*;

public class FuncCallNode extends BasicNode implements ExprNode, StmtNode {
    private IdentNode functionName = null;
    private List<ExprNode> params = null;
    Function func = null;

    public FuncCallNode(IdentNode functionName, Collection<ExprNode> params) {
        this.functionName = functionName;
        if (params != null) {
            this.params = new ArrayList<>();
            this.params.addAll(params);
        }
    }

    @Override
    public Collection<? extends AstNode> childs() {
        return params;
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
        if (func == null) {
            ArrayList<Type> types = new ArrayList<>();
            for (ExprNode arg : params) {
                arg.semanticCheck();
                types.add(arg.getType());
            }
            func = scope.getFunction(functionName.getName(), types);
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
        for (ExprNode arg : params) {
            arg.initialize(scope);
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
}