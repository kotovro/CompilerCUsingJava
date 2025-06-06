package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

public class ReturnNode extends BasicNode implements ExprNode, StmtNode {
    ExprNode expr;
    Scope scope;

    public ReturnNode(ExprNode expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "return";
    }

    @Override
    public void semanticCheck() {
        Type expectedType = scope.getCurrentFunctionReturnType();
        
        if (expr == null) {
            if (!expectedType.equals(Type.VOID)) {
                throw new SemanticException("Non-void function must return a value");
            }
            return;
        }

        expr.semanticCheck();
        Type actualType = expr.getType();
        
        if (!actualType.equals(expectedType) && !TypeConvertibility.canConvert(actualType, expectedType)) {
            throw new SemanticException("Return type mismatch: expected " + expectedType + " but got " + actualType);
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        if (expr != null) {
            expr.initialize(scope);
        }
    }

    @Override
    public Type getType() {
        return expr != null ? expr.getType() : null;
    }

    @Override
    public StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();
        if (expr != null) {
            code.append(expr.generateCode());
            code.append(expr.getType().getCommandWordPrefix()).append("return\n");
        } else {
            code.append("return\n");
        }
        return code;
    }
}
