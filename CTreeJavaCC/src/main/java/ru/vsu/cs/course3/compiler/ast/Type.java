package ru.vsu.cs.course3.compiler.ast;

public enum Type {
    INTEGER("int"),
    FLOAT("float"),
    STRING("string"),
    CHAR("char"),
    BOOLEAN("bool"),
    DOUBLE("double"),
    VOID("void");

    private final String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static Type fromString(String name) {
        for (Type op : Type.values()) {
            if (op.name.equals(name.toLowerCase())) {
                return op;
            }
        }
        throw new IllegalArgumentException(name);
    }
}
