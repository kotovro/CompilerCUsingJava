package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

public class Variable implements Comparable<Variable> {
    @Override
    public int compareTo(Variable o) {
        return name.compareTo(o.name);
    }

    private Type type;
    private String name;
    private String identifier;
    private ScopeType scopeType;
    private int index;

    public Variable(String name, Type type, ScopeType scopeType, int index) {
        this.name = name;
        this.type = type;
        this.scopeType = scopeType;
        this.index = index;
    }

    public Variable(String name, Type type, String identifier) {
        this.type = type;
        this.name = name;
        this.identifier = identifier;
        this.global = true;
    }


    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ScopeType getScopeType() {
        return scopeType;
    }

    public int getIndex() {
        return index;
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

    public StringBuilder generateGetCode() {
        StringBuilder code = new StringBuilder();
        if (scopeType == ScopeType.GLOBAL) {
            code.append("getstatic ").append(name).append(" ").append(type.getAbbreviation());
        } else {
            code.append(type.getCommandWordPrefix()).append("load ").append(index);
        }
        code.append("       ;").append(name).append("\n");
        return code;
    }

    public StringBuilder generatePutCode() {
        StringBuilder code = new StringBuilder();
        if (scopeType == ScopeType.GLOBAL) {
            code.append("putstatic ").append(name).append(" ").append(type.getAbbreviation());
        } else {
            code.append(type.getCommandWordPrefix()).append("store ").append(index);
        }
        code.append("       ;").append(name).append("\n");
        return code;
    }
}
