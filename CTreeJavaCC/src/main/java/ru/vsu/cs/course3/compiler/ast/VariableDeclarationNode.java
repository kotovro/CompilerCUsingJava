package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VariableDeclarationNode extends BasicNode implements StmtNode {

    TypeNode type;
    IdentNode identifier;

    public VariableDeclarationNode(TypeNode type, IdentNode identifier) {
        this.type = type;
        this.identifier = identifier;
    }

    @Override
    public Collection<AstNode> childs() {
        List<AstNode> children = new ArrayList<AstNode>();
        children.add(type);
        children.add(identifier);
        return children;
    }

    TypeNode getType () {
        return type;
    }

    IdentNode getIdentifier () {
        return identifier;
    }

    @Override
    public String toString() {
        return "=";
    }

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        this.scope.addVariable(identifier.getName(), Type.fromString(type.getName()));
    }

    @Override
    public StringBuilder generateCode() {
        return null;
    }
}
