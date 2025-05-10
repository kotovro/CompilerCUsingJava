package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;

import java.util.*;

public class IfNode extends BasicNode implements ExprNode, StmtNode {
    private ExprNode cond = null;
    private StmtNode thenStmt = null;
    private StmtNode elseStmt = null;

    public IfNode(ExprNode cond, StmtNode thenStmt, StmtNode elseStmt) {
        this.cond = cond;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public Collection<AstNode> childs() {
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

    @Override
    public void semanticCheck() {
        cond.semanticCheck();
        if (!cond.getType().equals(Type.BOOLEAN) && !TypeConvertibility.canConvert(cond.getType(), Type.BOOLEAN)) {
            throw new SemanticException("If condition should be boolean");
        }
        elseStmt.semanticCheck();
    }

    @Override
    public void initialize(Scope scope) {

    }

    @Override
    public Type getType() {
        return null; //not needed
    }
}