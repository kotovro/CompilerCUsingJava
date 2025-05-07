package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

import java.util.List;

public class Function implements Comparable<Function> {
    @Override
    public int compareTo(Function o) {
        int a = name.compareTo(o.name);
        int b = (parameters.size() - o.parameters.size());
        if (a != 0) return a;
        if (b != 0) return b;
        int c;
        for (int i = 0; i < parameters.size(); i++) {
            c = parameters.get(i).compareTo(o.parameters.get(i));
            if (c != 0) return c;
        }
        return 0;
    }

    private String name;
    private Type type;
    private List<Type> parameters;

    public Function(String name, List<Type> parameters, Type returnType) {
        this.name = name;
        this.parameters = parameters;
        this.type = returnType;
    }

    public Type getReturnType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Function) {
            Function other = (Function) obj;
            return this.name.equals(other.name) && this.parameters.equals(other.parameters);
        }
        return false;
    }
}
