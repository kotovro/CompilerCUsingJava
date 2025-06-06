package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;

import java.util.Arrays;
import java.util.Collection;
import java.lang.StringBuilder;

public class BinaryOpNode extends BasicNode implements ExprNode {

    private BinaryOperator op;
    private ExprNode arg1;
    private ExprNode arg2;
    Type type = Type.VOID;

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


    @Override
    public void semanticCheck() {
        arg1.semanticCheck();
        arg2.semanticCheck();

        for (var types : op.supportableTypes()) {
            if (arg1.getType().equals(types.typeLeft) && arg2.getType().equals(types.typeRight)) {
                type = op.getReturnType(arg1.getType(), arg2.getType());
                return;
            }
        }

        for (var types : op.supportableTypes()) {
            if ((arg1.getType().equals(types.typeLeft) || (TypeConvertibility.canConvert(arg1.getType(), types.typeLeft)))
                    && (arg2.getType().equals(types.typeRight) || TypeConvertibility.canConvert(arg2.getType(), types.typeRight))) {
                if (!arg1.getType().equals(types.typeLeft)) {
                    arg1 = new CastNode(types.typeLeft, arg1, scope);
                    arg1.initialize(scope);
                }
                if (!arg2.getType().equals(types.typeRight)) {
                    arg2 = new CastNode(types.typeRight, arg2, scope);
                    arg2.initialize(scope);
                }

                type = op.getReturnType(arg1.getType(), arg2.getType());
                return;
            }
        }
        throw new SemanticException("Operation " + op.name() + " is not supported with " + arg1.getType() + " and " + arg2.getType());
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        arg1.initialize(scope);
        arg2.initialize(scope);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();

        // Generate code for the operands (pushing their values onto the stack)
        code.append(arg1.generateCode());
        code.append(arg2.generateCode());

        // Get and append the Jasmin code for the operation
        // Use the types after potential implicit conversions inserted during semanticCheck
        code.append(op.getOperatorCode(arg1.getType(), arg2.getType(), scope));

        return code;
    }
}
