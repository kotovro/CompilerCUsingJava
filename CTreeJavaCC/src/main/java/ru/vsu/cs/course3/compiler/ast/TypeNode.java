package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class TypeNode extends BasicNode implements AstNode {
    private String name;

    public TypeNode(String name) {
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
}
