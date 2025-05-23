package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.LocalScope;

import java.util.*;

public class StmtListNode extends BasicNode implements StmtNode {
    public List<StmtNode> stmts = new ArrayList<>();

    public StmtListNode(Collection<StmtNode> stmts) {
        if (stmts != null) {
            this.stmts.addAll(stmts);
        }
    }

    public StmtListNode(StmtNode ...stmts) {
        this(Arrays.asList(stmts));
    }

    @Override
    public Collection<? extends AstNode> childs() {
        return stmts;
    }

    @Override
    public String toString() {
        return "...";
    }

    public List<StmtNode> getStmts() {
        return Collections.unmodifiableList(stmts);
    }
    public void setStmts(List<StmtNode> stmts) {}

    @Override
    public void semanticCheck() {
        for (StmtNode statement : stmts) {
            statement.semanticCheck();
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        for (StmtNode stmt : stmts) {
            stmt.initialize(this.scope);
        }
    }

}
