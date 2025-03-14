package ru.vsu.cs.course3.compiler.ast;

import java.util.*;

public class ForNode implements ExprNode, StmtNode {
    private StmtNode init = null;
    private ExprNode cond = null;
    private StmtNode step = null;
    private StmtNode body = null;

    public ForNode(StmtNode init, ExprNode cond, StmtNode step, StmtNode body) {
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.body = body;
    }

    @Override
    public Collection<? extends AstNode> childs() {
        return Arrays.asList(init, cond, step, body);
    }

    @Override
    public String toString() {
        return "for";
    }

    public StmtNode getInit() {
        return init;
    }

    public ExprNode getCond() {
        return cond;
    }

    public StmtNode getStep() {
        return step;
    }

    public StmtNode getBody() {
        return body;
    }
}