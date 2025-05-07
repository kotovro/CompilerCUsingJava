package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class ReturnNode extends BasicNode implements ExprNode, StmtNode {
    ExprNode expr;

    public ReturnNode(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }
}
