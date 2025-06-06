package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.List;

public interface Scope {
    void addVariable(String name, Type type);
    Variable getVariable(String name);
    boolean contains(Variable var);
    void addFunction(Function function);
    Function getFunction(String name, List<Type> types);
    Type getCurrentFunctionReturnType();
    int getFreeOperatorIdentifier();
    int getFreeLabelIdentifier();
    int getTotalLocals();
}
