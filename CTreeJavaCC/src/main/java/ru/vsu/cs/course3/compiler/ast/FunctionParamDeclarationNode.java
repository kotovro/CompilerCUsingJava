package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class FunctionParamDeclarationNode extends BasicNode implements AstNode{
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

    @Override
    public void semanticCheck() {
        identifier.semanticCheck();
    }

    @Override
    public void initialize(Scope scope) {
        identifier.initialize(scope);
    }
}
