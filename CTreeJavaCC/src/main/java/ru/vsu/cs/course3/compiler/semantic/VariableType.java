package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

public class VariableType {
    public static Type getType(String literal) {
        if (literal == null || literal.isEmpty()) {
            throw new IllegalArgumentException("Input string cannot be null or empty");
        }

        if (literal.length() == 3 && literal.startsWith("'") && literal.endsWith("'")) {
            return Type.CHAR;
        }

        if (literal.length() > 3 && literal.startsWith("'") && literal.endsWith("'")) {
            return Type.STRING;
        }

        if (literal.equals("true") || literal.equals("false")) {
            return Type.BOOLEAN;
        }

        try {
            Integer.parseInt(literal);
            return Type.INTEGER;
        } catch (NumberFormatException e) {
        }

        try {
            Double.parseDouble(literal);
            return Type.FLOAT;
        } catch (NumberFormatException e) {
        }

        throw new IllegalArgumentException("Cannot resolve type for : " + literal);
    }
}
