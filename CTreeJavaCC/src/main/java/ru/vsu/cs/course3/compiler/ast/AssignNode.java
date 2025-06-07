package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.exceptions.SemanticException;
import ru.vsu.cs.course3.compiler.semantic.Scope;
import ru.vsu.cs.course3.compiler.semantic.TypeConvertibility;
import ru.vsu.cs.course3.compiler.semantic.Variable;
import ru.vsu.cs.course3.compiler.semantic.ScopeType;

import java.util.*;
import java.lang.StringBuilder;
import java.util.HashMap;

public class AssignNode extends BasicNode implements StmtNode {
    private TypeNode type = null;
    private IdentNode ident = null;
    private ExprNode expr = null;
    private Scope scope;

    // Temporary map for Jasmin type descriptors - should be centralized
    private static HashMap<Type, String> JASMIN_TYPE_DESCRIPTORS = new HashMap<>();

    static {
        JASMIN_TYPE_DESCRIPTORS.put(Type.VOID, "V");
        JASMIN_TYPE_DESCRIPTORS.put(Type.INTEGER, "I");
        JASMIN_TYPE_DESCRIPTORS.put(Type.FLOAT, "F"); // Assuming float maps to F
        JASMIN_TYPE_DESCRIPTORS.put(Type.DOUBLE, "D"); // Use D for double
        JASMIN_TYPE_DESCRIPTORS.put(Type.BOOLEAN, "Z"); // Boolean is Z
        JASMIN_TYPE_DESCRIPTORS.put(Type.STRING, "Ljava/lang/String;"); // String descriptor
        JASMIN_TYPE_DESCRIPTORS.put(Type.CHAR, "C"); // Char is C
    }

    public AssignNode(TypeNode type, IdentNode ident, ExprNode expr) {
        this.type = type;
        this.ident = ident;
        this.expr = expr;
    }

    public AssignNode(IdentNode ident, ExprNode expr) {
        this.ident = ident;
        this.expr = expr;
    }

    public AssignNode(IdentNode ident) {
        this.ident = ident;
    }

    @Override
    public Collection<AstNode> childs() {
        List<AstNode> children = new ArrayList<AstNode>();
        if (type != null) {
            children.add(type);
        }
        children.add(ident);
        if (expr != null) {
            children.add(expr);
        }
        return children;
    }

    @Override
    public String toString() {
        return "=";
    }

    public IdentNode getIdent() {
        return ident;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void semanticCheck() {
        if (expr != null) {
            expr.semanticCheck();
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        if (type != null) {
            try {
                scope.addVariable(ident.getName(), Type.fromString(type.toString()));
                ident.variable = scope.getVariable(ident.getName());
            } catch (SemanticException e) {
                System.err.println("Semantic error in initialize: " + e.getMessage());
            }
        } else {
            try {
                ident.variable = scope.getVariable(ident.getName());
            } catch (SemanticException e) {
                System.err.println("Semantic error in initialize: " + e.getMessage());
            }
        }

        if (expr != null) {
            expr.initialize(scope);
        }
    }

    @Override
    public StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();
        code.append(expr.generateCode());
        code.append(ident.variable.generatePutCode());
        return code;
    }
}
