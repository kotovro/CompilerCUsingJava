package ru.vsu.cs.course3.compiler.ast;

import java.util.*;

public class IfNode implements ExprNode, StmtNode {
    private ExprNode cond = null;
    private StmtNode thenStmt = null;
    private StmtNode elseStmt = null;

    public IfNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public Collection<? extends AstNode> childs() {
        List<AstNode> childs = new ArrayList<>(Arrays.asList(cond, thenStmt));
        if (elseStmt != null) {
            childs.add(elseStmt);
        }
        return childs;
    }

    @Override
    public String toString() {
        return "if";
    }

    public ExprNode getCond() {
        return cond;
    }

    public StmtNode getThenStmt() {
        return thenStmt;
    }

    public StmtNode getElseStmt() {
        return elseStmt;
    }
}