package ru.vsu.cs.course3.compiler.ast;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WhileNode implements ExprNode, StmtNode {
    private ExprNode cond = null;
    private StmtNode body = null;

    public WhileNode(final ExprNode cond, final StmtNode body) {
        this.cond = cond;
        this.body = body;
    }

    @Override
    public Collection<? extends AstNode> childs() {
        return Arrays.asList(cond, body);
    }

    @Override
    public String toString() {
        return "while";
    }

    public ExprNode getCond() {
        return cond;
    }

    public StmtNode getBody() {
        return body;
    }

}
