package ru.vsu.cs.course3.compiler.ast;

public class FunctionParamDeclarationNode implements AstNode {
    private TypeNode type;
    private IdentNode identifier;

    public FunctionParamDeclarationNode(TypeNode type, IdentNode identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    TypeNode getType() {
        return type;
    }

    IdentNode getIdentifier() {
        return identifier;
    }

}
