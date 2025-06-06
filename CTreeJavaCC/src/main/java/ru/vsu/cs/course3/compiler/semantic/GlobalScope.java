package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;
import ru.vsu.cs.course3.compiler.exceptions.SemanticException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class GlobalScope implements Scope {
    private Set<Variable> variables;
    private Set<Function> functions;
    private Function currentFunction;
    private int labelCounter = 0;
    private int operatorCounter = 0;

    public GlobalScope() {
        variables = new TreeSet<>();
        functions = new TreeSet<>();
        currentFunction = null;
    }

    public void setCurrentFunction(Function function) {
        this.currentFunction = function;
    }

    @Override
    public Type getCurrentFunctionReturnType() {
        if (currentFunction == null) {
            throw new SemanticException("Return statement outside of function");
        }
        return currentFunction.getReturnType();
    }

    @Override
    public void addFunction(Function callable) throws SemanticException {
        if (functions.contains(callable)) throw new SemanticException("This callable has already been declared");
        functions.add(callable);
    }

    @Override
    public Function getFunction(String name, List<Type> types) throws SemanticException {
        name = name.toLowerCase();
        List<Function> seive = new ArrayList<>();
        for (Function callable : functions) {
            if (!callable.getName().equals(name)) continue;
            if (convertable(types, callable.getParameters())) seive.add(callable);
        }
        int max = 0;
        int count;
        List<Function> result = new ArrayList<>();
        for (Function callable : seive) {
            count = 0;
            for (int i = 0; i < callable.getParameters().size(); i++) {
                if (types.get(i).equals(callable.getParameters().get(i))) count++;
            }
            if (count > max) {
                max = count;
                result.clear();
            }
            if (count == max) result.add(callable);
        }
        if (result.isEmpty()) throw new SemanticException("None callables with name " + name + " can be called");
        if (result.size() > 1) throw new SemanticException("Multiple callables with name " + name + " can be called");
        return result.getLast();
    }

    private boolean convertable(List<Type> types, List<Type> parameters) {
        if (parameters.size() != types.size()) return false;
        for (int i = 0; i < types.size(); i++) {
            if (!(types.get(i).equals(parameters.get(i)) || TypeConvertibility.canConvert(types.get(i), parameters.get(i))))
                return false;
        }
        return true;
    }

    @Override
    public Variable getVariable(String name) throws SemanticException {
        for (Variable i : variables) {
            if (i.getName().equals(name.toLowerCase())) return i;
        }
        throw new SemanticException("Variable \"" + name + "\" is not found");
    }

    @Override
    public void addVariable(String name, Type type) throws SemanticException {
        Variable i = new Variable(name.toLowerCase(), type, ScopeType.GLOBAL, 0);
        if (variables.contains(i)) throw new SemanticException("Variable is already declared");
        variables.add(i);
    }

    @Override
    public boolean contains(Variable var) {
        return variables.contains(var);
    }

    @Override
    public int getFreeLabelIdentifier() {
        return labelCounter++;
    }

    @Override
    public int getFreeOperatorIdentifier() {
        return operatorCounter++;
    }

    @Override
    public int getTotalLocals() {
        return 0; // Global scope has no local variables
    }
}
