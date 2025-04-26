package ru.vsu.cs.course3.compiler.ast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VariableDeclarationNode implements StmtNode {

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
}
