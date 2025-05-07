package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.Arrays;
import java.util.Collection;

public class BinaryOpNode extends BasicNode implements ExprNode {

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }

    public static BinaryOperator BinOp;

    private BinaryOperator op;
    private ExprNode arg1;
    private ExprNode arg2;

    public BinaryOpNode(String op, ExprNode arg1, ExprNode arg2) {
        this.op = Arrays.stream(BinaryOperator.values()).filter(x -> x.toString().equals(op)).findFirst().get();
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public Collection<ExprNode> childs() {
        return Arrays.asList(arg1, arg2);
    }

    @Override
    public String toString() {
        return op.toString();
    }

    public BinaryOperator getOp() {
        return op;
    }

    public ExprNode getArg1() {
        return arg1;
    }

    public ExprNode getArg2() {
        return arg2;
    }
}
