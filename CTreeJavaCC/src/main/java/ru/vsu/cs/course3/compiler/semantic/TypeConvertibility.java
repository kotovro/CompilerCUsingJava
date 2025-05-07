package ru.vsu.cs.course3.compiler.semantic;

import ru.vsu.cs.course3.compiler.ast.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeConvertibility {
    private static class TypePair {
        Type typeFrom, typeTo;

        public TypePair(Type typeFrom, Type typeTo) {
            this.typeFrom = typeFrom;
            this.typeTo = typeTo;
        }

        public boolean equals(TypePair typePair) {
            return typeFrom == typePair.typeFrom && typeTo == typePair.typeTo;
        }
    }

    private static final List<TypePair> typePairs;
    static {
        typePairs = new ArrayList<>();

        typePairs.add(new TypePair(Type.INTEGER, Type.FLOAT));
        typePairs.add(new TypePair(Type.INTEGER, Type.BOOLEAN));
        typePairs.add(new TypePair(Type.INTEGER, Type.CHAR));

        typePairs.add(new TypePair(Type.FLOAT, Type.CHAR));
        typePairs.add(new TypePair(Type.FLOAT, Type.INTEGER));
        typePairs.add(new TypePair(Type.FLOAT, Type.BOOLEAN));

        typePairs.add(new TypePair(Type.BOOLEAN, Type.CHAR));
        typePairs.add(new TypePair(Type.BOOLEAN, Type.FLOAT));
        typePairs.add(new TypePair(Type.BOOLEAN, Type.INTEGER));

        typePairs.add(new TypePair(Type.CHAR, Type.STRING));
        typePairs.add(new TypePair(Type.CHAR, Type.BOOLEAN));
        typePairs.add(new TypePair(Type.CHAR, Type.INTEGER));
        typePairs.add(new TypePair(Type.CHAR, Type.FLOAT));
    }

    public static boolean canConvert(Type typeFrom, Type typeTo) {
        TypePair pair = new TypePair(typeFrom, typeTo);
        for (TypePair curPair : typePairs) {
            if (curPair.equals(pair)) {
                return true;
            }
        }
        return false;
    }
}
