package ru.vsu.cs.course3.compiler.ast;

import java.util.*;

public class ForNode implements ExprNode, StmtNode {
    private StmtNode init = null;
    private ExprNode cond = null;
    private ExprNode step = null;
    private StmtNode body = null;

    public ForNode(StmtNode init, ExprNode cond, ExprNode step, StmtNode body) {
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.body = body;
    }

    @Override
    public Collection<? extends AstNode> childs() {
        List<AstNode> nodes = new ArrayList<>();
        nodes.add(body);
        if (init != null) {
            nodes.add(init);
        }
        if (cond != null) {
            nodes.add(cond);
        }
        if (step != null) {
            nodes.add(step);
        }

        return nodes;
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

    public ExprNode getStep() {
        return step;
    }

    public StmtNode getBody() {
        return body;
    }
}