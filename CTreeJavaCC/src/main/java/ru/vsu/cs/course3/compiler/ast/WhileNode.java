package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.LocalScope;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;

import java.util.Arrays;
import java.util.Collection;

public class WhileNode extends BasicNode implements StmtNode {
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

    @Override
    public void semanticCheck() {
        cond.semanticCheck();
        if (!cond.getType().equals(Type.BOOLEAN) && !TypeConvertibility.canConvert(cond.getType(), Type.BOOLEAN)) {
            throw new SemanticException("If condition should be boolean");
        }
        body.semanticCheck();
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = new LocalScope(scope);
        cond.initialize(scope);
        body.initialize(this.scope);
    }

}
