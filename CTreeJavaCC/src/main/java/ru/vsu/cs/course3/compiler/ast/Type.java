package ru.vsu.cs.course3.compiler.ast;

public enum Type {
    INTEGER("int", "I", "i"),
    FLOAT("float", "F", "f"),
    STRING("string", "Ljava/lang/String;", "a"),
    CHAR("char", "C", "c"),
    BOOLEAN("bool", "Z", "z"),
    DOUBLE("double", "D", "d"),
    VOID("void", "V", "v");

    private final String name;
    private final String abbreviation;
    private final String commandWordPrefix;

    Type(String name, String abbreviation, String commandWordPrefix) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.commandWordPrefix = commandWordPrefix;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getCommandWordPrefix() {
        return commandWordPrefix;
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
