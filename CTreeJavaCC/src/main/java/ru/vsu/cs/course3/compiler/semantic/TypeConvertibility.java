package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeConvertibility {
    private static class TypePair {
        Type typeFrom, typeTo;
        StringBuilder command;

        public TypePair(Type typeFrom, Type typeTo, String command) {
            this.typeFrom = typeFrom;
            this.typeTo = typeTo;
            this.command = new StringBuilder(command);
        }

        public boolean equals(TypePair typePair) {
            return typeFrom == typePair.typeFrom && typeTo == typePair.typeTo;
        }
    }
    private static final List<TypePair> typePairs;
    static {
        typePairs = new ArrayList<>();

        typePairs.add(new TypePair(Type.INTEGER, Type.FLOAT, "i2f\n"));
        typePairs.add(new TypePair(Type.INTEGER, Type.BOOLEAN, "i2b\n"));
        typePairs.add(new TypePair(Type.INTEGER, Type.CHAR, "i2c\n"));

        typePairs.add(new TypePair(Type.FLOAT, Type.CHAR, "f2i\ni2c\n"));
        typePairs.add(new TypePair(Type.FLOAT, Type.INTEGER, "f2i\n"));
        typePairs.add(new TypePair(Type.FLOAT, Type.BOOLEAN, "f2i\ni2b\n"));

        typePairs.add(new TypePair(Type.BOOLEAN, Type.CHAR, "i2c\n"));
        typePairs.add(new TypePair(Type.BOOLEAN, Type.FLOAT, "i2f\n"));
        typePairs.add(new TypePair(Type.BOOLEAN, Type.INTEGER, ""));

        typePairs.add(new TypePair(Type.CHAR, Type.STRING, "invokestatic java/lang/String/valueOf(C)Ljava/lang/String;\n"));
        typePairs.add(new TypePair(Type.CHAR, Type.BOOLEAN, "i2b\n"));
        typePairs.add(new TypePair(Type.CHAR, Type.INTEGER, ""));
        typePairs.add(new TypePair(Type.CHAR, Type.FLOAT, "i2f\n"));
    }


    public static boolean canConvert(Type typeFrom, Type typeTo) {
        TypePair pair = new TypePair(typeFrom, typeTo, "");
        for (TypePair curPair : typePairs) {
            if (curPair.equals(pair)) {
                return true;
            }
        }
        return false;
    }

    public static StringBuilder generateCode(Type typeFrom, Type typeTo) {
        TypePair pair = new TypePair(typeFrom, typeTo, "");
        for (TypePair curPair : typePairs) {
            if (curPair.equals(pair)) {
                return curPair.command;
            }
        }
        throw new RuntimeException("Can't convert " + typeFrom + " to " + typeTo);
    }
}
