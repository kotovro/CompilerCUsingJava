package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.Variable;

public class IdentNode extends BasicNode implements ExprNode {
    private String name;
    Variable variable;
    Scope scope;
    public IdentNode(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    @Override
    public void semanticCheck() {
        variable = scope.getVariable(name);

    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
    }

    @Override
    public StringBuilder generateCode() {
        return new StringBuilder("place holder!!! from ident node");
    }

    @Override
    public Type getType() {
        return scope.getVariable(name).getType();
    }
}
