package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class ValueNode extends BasicNode implements ExprNode {
    private String str;
    private Object value;
    Type valueType;

    public ValueNode(String str) {
        this.str = str;
        if (str.equals("true") || str.equals("false")) {
            this.value = Boolean.valueOf(str);
            this.valueType = Type.BOOLEAN;
        } else if (str.startsWith("\"") || str.startsWith("'")) {
            this.value = str.substring(1, str.length() - 1);
            this.valueType = Type.STRING;
        } else if (str.contains(".")) {
            this.value = Float.valueOf(str);
            this.valueType = Type.FLOAT;
        } else {
            this.value = Integer.valueOf(str);
            this.valueType = Type.INTEGER;
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
        return new StringBuilder();
    }

    @Override
    public Type getType() {
        return valueType;
    }
}