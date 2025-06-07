package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.Arrays;
import java.util.Collection;

public class UnaryOperationNode extends BasicNode implements ExprNode, StmtNode{
    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }
    @Override
    public StringBuilder generateCode() {
        return null;
    }

    @Override
    public Type getType() {
        return null;
    }

    public static enum UnOp {
        ADD("++"),
        SUB("--");


        public final String op;

        UnOp(String op) {
            this.op = op;
        }

        @Override
        public String toString() {
            return op;
        }
    }

    private UnaryOperationNode.UnOp op;
    private AstNode arg;
    private boolean isPreIncrement;

    public UnaryOperationNode(String op, ExprNode arg, boolean isPreIncrement) {
        this.op = Arrays.stream(UnaryOperationNode.UnOp.values()).filter(x -> x.op.equals(op)).findFirst().get();
        this.arg = arg;
        this.isPreIncrement = isPreIncrement;
    }

    @Override
    public Collection<AstNode> childs() {
        return Arrays.asList(arg);
    }

    @Override
    public String toString() {
        return op.toString();
    }

    public UnaryOperationNode.UnOp getOp() {
        return op;
    }

    public AstNode getArg() {
        return arg;
    }
}
