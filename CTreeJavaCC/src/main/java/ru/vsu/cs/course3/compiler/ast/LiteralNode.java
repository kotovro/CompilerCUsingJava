package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.VariableType;

public class LiteralNode extends BasicNode implements ExprNode {
    private String str;
    private Object value;

    public LiteralNode(String str) {
        this.str = str;
        if (str.equals("true") || str.equals("false")) {
            this.value = Boolean.valueOf(str);
        } else if (str.startsWith("\"") || str.startsWith("'")) {
            this.value = str.substring(1, str.length() - 1);
        } else if (str.contains(".")) {
            this.value = Double.valueOf(str);
        } else {
            this.value = Integer.valueOf(str);
        }
    }

    @Override
    public String toString() {
        return str;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public void semanticCheck() {

    }

    @Override
    public void initialize(Scope scope) {

    }

    @Override
    public StringBuilder generateCode() {
        Type type = VariableType.getType(str);
        if (type == Type.STRING) {
            StringBuilder sb = new StringBuilder(str);
            sb.setCharAt(0, '"');
            sb.setCharAt(sb.length() - 1, '"');
            return new StringBuilder().append("ldc ").append(sb).append("\n");
        }
        if (type == Type.DOUBLE) {
            StringBuilder sb = new StringBuilder();
            sb.append("f");
            return new StringBuilder().append("ldc ").append(sb).append("\n");
        }
        if (type == Type.BOOLEAN) {
            if ("true".equalsIgnoreCase(str)) {
                return new StringBuilder().append("ldc ").append(1).append("\n");
            } else {
                return new StringBuilder().append("ldc ").append(0).append("\n");
            }
        }
        if (type == Type.CHAR) {
            int a = str.charAt(1);
            return new StringBuilder().append("ldc ").append(a).append("\n");
        }
        return new StringBuilder().append("ldc ").append(value).append("\n");
    }

    @Override
    public Type getType() {
        return VariableType.getType(str);
    }
}