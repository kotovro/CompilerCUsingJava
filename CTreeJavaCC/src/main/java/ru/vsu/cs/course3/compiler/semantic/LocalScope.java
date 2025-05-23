package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LocalScope implements Scope{
    private Scope parent;
    private Set<Variable> variables;

    public LocalScope(Scope parent) {
        this.parent = parent;
        variables = new TreeSet<>();
    }

    @Override
    public Variable getVariable(String name) {
        for (Variable i : variables) {
            if (i.getName().equals(name.toLowerCase())) return i;
        }
        return parent.getVariable(name);
    }

    @Override
    public void addVariable(String name, Type type) {
        Variable i = new Variable(name.toLowerCase(), type);
        if (variables.contains(i)) throw new SemanticException("Variable is already declared: " + name);
        variables.add(i);
    }

    @Override
    public boolean contains(Variable var) {
        return variables.contains(var);
    }

    @Override
    public void addFunction(Function callable) {
        parent.addFunction(callable);
    }

    @Override
    public Function getFunction(String name, List<Type> parameters) {
        return parent.getFunction(name, parameters);
    }

    @Override
    public Type getCurrentFunctionReturnType() {
        return parent.getCurrentFunctionReturnType();
    }
}
