package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.*;

public class FuncCallNode extends BasicNode implements ExprNode, StmtNode {
    private IdentNode func = null;
    private List<ExprNode> params = null;

    public FuncCallNode(IdentNode func, Collection<ExprNode> params) {
        this.func = func;
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
        return func.toString() + "()";
    }

    public List<ExprNode> getParams() {
        return Collections.unmodifiableList(params);
    }

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }
}