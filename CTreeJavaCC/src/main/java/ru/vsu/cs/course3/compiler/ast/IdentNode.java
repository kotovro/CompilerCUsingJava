package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class IdentNode extends BasicNode implements ExprNode {
    private String name;

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

    }

    @Override
    public void initialize(Scope scope) {

    }

    @Override
    public Type getType() {
        return null;
    }
}
