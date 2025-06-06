package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class LocalScope implements Scope{
    private Scope parent;
    public Set<Variable> variables;
    private int operatorCounter = 0;
    private int labelCounter = 0;

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
        Variable i = new Variable(name.toLowerCase(), type, ScopeType.LOCAL, variables.size());
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

    public Set<Variable> getVariables() {
        return variables;
    }

    @Override
    public int getTotalLocals() {
        // Assuming variables set includes parameters and local variables
        return variables.size();
    }

    @Override
    public int getFreeOperatorIdentifier() {
        // Local scope might need temporary variable identifiers
        return operatorCounter++;
    }

    @Override
    public int getFreeLabelIdentifier() {
        // Local scope needs its own label identifiers
        return labelCounter++;
    }
}
