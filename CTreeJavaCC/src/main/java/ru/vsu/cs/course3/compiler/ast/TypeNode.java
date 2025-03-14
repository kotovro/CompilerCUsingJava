package ru.vsu.cs.course3.compiler.ast;

public class TypeNode implements AstNode {
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
}
