package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

public class Variable implements Comparable<Variable> {
    @Override
    public int compareTo(Variable o) {
        return name.compareTo(o.name);
    }

    private Type type;
    private String name;

    public Variable(String name, Type type) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            Variable var = (Variable) obj;
            return var.getName().equals(name);
        }
        return super.equals(obj);
    }

    public void setType(Type type) {
        this.type = type;
    }
}
