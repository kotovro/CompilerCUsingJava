package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;

public class CastNode  extends BasicNode implements ExprNode {

    Type toType;
    ExprNode expr;

    public CastNode(Type toType, ExprNode expr) {
        this.toType = toType;
        this.expr = expr;
    }

    public CastNode(Type toType, ExprNode expr, Scope scope) {
        this.toType = toType;
        this.expr = expr;
        this.scope = scope;
    }

    @Override
    public void semanticCheck() {
        expr.semanticCheck();
        if (!TypeConvertibility.canConvert(expr.getType(), toType)) {
            throw new SemanticException("Type convertibility failed: from " + expr.getType() + " to " + toType);
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        this.expr.initialize(scope);
    }


    @Override
    public Type getType() {
        return toType;
    }

}
