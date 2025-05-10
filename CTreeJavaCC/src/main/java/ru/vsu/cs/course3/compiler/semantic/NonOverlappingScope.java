package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class NonOverlappingScope  implements Scope {
    private Scope parent;
    private Set<Variable> variables;


    public NonOverlappingScope(Scope parent) {
        this.parent = parent;
        variables = new TreeSet<>();
    }

    @Override
    public void addVariable(String name, Type type) {
        Variable i = new Variable(name.toLowerCase(), type);
        if (contains(i)) throw new SemanticException("Variable is already declared");
        variables.add(i);
    }



    @Override
    public Variable getVariable(String name) {
        for (Variable i : variables) {
            if (i.getName().equals(name.toLowerCase())) return i;
        }
        return parent.getVariable(name);
    }

    @Override
    public boolean contains(Variable var) {
        return variables.contains(var) || parent.contains(var);
    }

    @Override
    public void addFunction(Function function) {
        parent.addFunction(function);
    }

    @Override
    public Function getFunction(String name, List<Type> parameterTypes) {
        return parent.getFunction(name, parameterTypes);
    }
}
