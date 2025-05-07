package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.*;

public class AssignNode extends BasicNode implements StmtNode {
    private TypeNode type = null;
    private IdentNode ident = null;
    private ExprNode expr = null;


    public AssignNode(TypeNode type, IdentNode ident, ExprNode expr) {
        this.type = type;
        this.ident = ident;
        this.expr = expr;
    }

    public AssignNode(IdentNode ident, ExprNode expr) {
        this.ident = ident;
        this.expr = expr;
    }

    public AssignNode(IdentNode ident) {
        this.ident = ident;
    }

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

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }
}
