package ru.vsu.cs.course3.compiler.exceptions;

public class SemanticException extends RuntimeException {
    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(String message, Integer row, Integer col) {
        super(getMessage(message, row, col));
    }

    private static String getMessage(String message, Integer row, Integer col) {
        if (row != null || col != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(" (");
            if (row != null) {
                sb.append(String.format("строка: %d", row));
                if (col != null) {
                    sb.append(", ");
                }
            }
            if (col != null) {
                sb.append(String.format("позиция: %d", col));
            }
            message += sb.toString();
        }
        return message;
    }
}
