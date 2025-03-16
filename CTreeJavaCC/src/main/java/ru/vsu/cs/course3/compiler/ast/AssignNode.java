package ru.vsu.cs.course3.compiler.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

    public AssignNode(IdentNode ident) {
        this.ident = ident;
    }
    /// modified it from simple return Arrays.asList(ident, expr)
    /// By doing several if statements, we don't have to modify tree printig method - otherwise we had to add null check fo r children
    @Override
    public Collection<AstNode> childs() {
        List<AstNode> children = new ArrayList<AstNode>();
        if (type != null) {
            children.add(type);
        }
        children.add(ident);
        if (expr != null) {
            children.add(expr);
        }
        return children;
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
