package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.LocalScope;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;

import java.io.PrintStream;
import java.util.*;

public class ForNode extends BasicNode implements StmtNode {
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

    @Override
    public void semanticCheck() {
        if (init != null) {
            init.semanticCheck();
        }
        if (cond != null) {
            cond.semanticCheck();
            if (!cond.getType().equals(Type.BOOLEAN) && !TypeConvertibility.canConvert(cond.getType(), Type.BOOLEAN)) {
                throw new SemanticException("For loop condition must be boolean");
            }
        }
        body.semanticCheck();
        if (step != null) {
            step.semanticCheck();
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = new LocalScope(scope);
        if (init != null) {
            init.initialize(this.scope);
        }
        body.initialize(this.scope);
        if (cond != null) {
            cond.initialize(this.scope);
        }
        if (step != null) {
            step.initialize(this.scope);
        }
    }


    @Override
    public void printTree(PrintStream printStream) {
        // Implementation not needed for code generation
    }
}