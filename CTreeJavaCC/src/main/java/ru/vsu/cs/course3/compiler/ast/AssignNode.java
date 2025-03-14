package ru.vsu.cs.course3.compiler.ast;

import java.util.Arrays;
import java.util.Collection;

public class AssignNode implements StmtNode {
    private IdentNode ident;
    private ExprNode expr = null;
    ///adds TypeNode
    private TypeNode type = null;

    ///added TypeNode cosntructor
    public AssignNode(TypeNode type, IdentNode ident, ExprNode expr) {
        this.type = type;
        this.ident = ident;
        this.expr = expr;
    }

    public AssignNode(TypeNode type, IdentNode ident) {
        this.type = type;
        this.ident = ident;
    }

    public AssignNode(IdentNode ident, ExprNode expr) {
        this.ident = ident;
        this.expr = expr;
    }

    /// modified it from simple return Arrays.asList(ident, expr)
    /// By doing several if statements, we don't have to modify tree printig method - otherwise we had to add null check fo r children
    @Override
    public Collection<AstNode> childs() {
        if (type != null) {
            if (expr != null) {
                return Arrays.asList(type, ident, expr);
            }
            return Arrays.asList(type, ident);
        }
        return Arrays.asList(ident, expr);
    }

    @Override
    public String toString() {
        return "=";
    }

    public IdentNode getIdent() {
        return ident;
    }

    public ExprNode getExpr() {
        return expr;
    }
}
