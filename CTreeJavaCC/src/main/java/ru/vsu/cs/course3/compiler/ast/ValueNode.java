package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

public class ValueNode extends BasicNode implements ExprNode {
    private String str;
    private Object value;

    public ValueNode(String str) {
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
}