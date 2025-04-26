package ru.vsu.cs.course3.compiler.ast;

public class ReturnNode implements ExprNode {
    ExprNode expr;

    public ReturnNode(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return";
    }
}
