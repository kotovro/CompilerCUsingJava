package ru.vsu.cs.course3.compiler.ast;

import java.util.*;

public class ForNode implements ExprNode, StmtNode {
    private StmtNode start = null;
    private ExprNode condition = null;
    private StmtNode counter = null;
    private StmtNode body = null;

    public ForNode(StmtNode start, ExprNode condition, StmtNode counter, StmtNode body) {
        this.start = start;
        this.condition = condition;
        this.counter = counter;
        this.body = body;
    }

    public ForNode(StmtNode body) {
        this.body = body;
    }

    @Override
    public Collection<? extends AstNode> childs() {
        List<AstNode> childs = Arrays.asList(body);
        if (condition != null) {
            System.out.println("condition " + condition);
            childs.add(condition);
        }
        if (start != null) {
            System.out.println("assign " + start);
            childs.add(start);
        }
        if (counter != null)    {
            System.out.println(counter);
            childs.add(counter);
        }
//        if (condition != null)
//            childs.add(condition);
//        if (counter != null)
//            childs.add(counter);
//        if (start != null)
//            childs.add(start);
//        if (elseStmt != null) {
//            childs.add(elseStmt);
//        }
        return childs;
    }

    @Override
    public String toString() {
        return "for";
    }

    public StmtNode getCond() {
        return start;
    }

    public ExprNode getThenStmt() {
        return condition;
    }

    public StmtNode getElseStmt() {
        return counter;
    }

    public StmtNode getStart() {
        return body;
    }
}