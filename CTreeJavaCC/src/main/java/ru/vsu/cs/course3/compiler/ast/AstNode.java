package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.io.PrintStream;
import java.util.Collection;

public interface AstNode {
    void semanticCheck();
    void initialize(final Scope scope);
    Collection<String> tree();
    void printTree(PrintStream printStream);
}
