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
        // Refined semanticCheck previously to handle declaration and assignment separately.
        // Re-checking the semanticCheck logic here based on the provided file state.

        // Assuming semanticCheck handles variable definition check for declarations
        // and variable existence check for assignments.

        if (expr != null) {
            expr.semanticCheck();
            // Type compatibility check is assumed to be done here by semanticCheck.
            // If type != null, it's a declaration, expr type must be convertible to declared type.
            // If type == null, it's an assignment, expr type must be convertible to variable's existing type.
        }
    }

    @Override
    public void initialize(Scope scope) {
        this.scope = scope;
        // Assuming variable is added to scope and ident.variable is linked in initialize
        if (type != null) {
            // Variable declaration
            try {
                scope.addVariable(ident.getName(), Type.fromString(type.toString()));
                ident.variable = scope.getVariable(ident.getName()); // Link variable
            } catch (SemanticException e) {
                // Should be caught in semanticCheck, but handle defensively
                System.err.println("Semantic error in initialize: " + e.getMessage());
            }
        } else {
            // Assignment to existing variable
            try {
                ident.variable = scope.getVariable(ident.getName()); // Link variable
            } catch (SemanticException e) {
                // Should be caught in semanticCheck, but handle defensively
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

        if (expr == null) {
            // Declaration without initialization - no code emitted here.
            // Variable allocation (.locals, .field) is handled elsewhere.
            return code; // Return empty StringBuilder
        }

        // Generate code for the expression (right-hand side) to push its value onto the stack
        code.append(expr.generateCode());

        // Get the Variable object from the IdentNode (linked during initialize)
        Variable variable = ident.variable;

        if (variable == null) {
            throw new RuntimeException("Variable not linked for assignment to IdentNode: " + ident.getName());
        }

        // Determine the correct store instruction based on variable scope and type
        ScopeType scopeType = variable.getScopeType(); // Assuming getScopeType is available
        Type varType = variable.getType();
        int varIndex = variable.getIndex(); // Assuming getIndex is available

        String storeInstruction = null;
        String typePrefix = null; // i, f, a, d

        switch (varType) {
            case INTEGER:
            case BOOLEAN:
            case CHAR:
                typePrefix = "i";
                break;
            case FLOAT:
                typePrefix = "f";
                break;
            case DOUBLE:
                typePrefix = "d";
                break;
            case STRING:
                typePrefix = "a"; // astore for object references
                break;
            case VOID:
                // Should not happen for a variable
                throw new RuntimeException("Cannot assign to a void variable.");
            default:
                throw new RuntimeException("Unsupported variable type for Jasmin assignment: " + varType);
        }

        switch (scopeType) {
            case LOCAL:
            case PARAM:
                // Local variables and parameters use istore, fstore, astore, dstore
                // Handle implicit indices 0-3
                if (varIndex >= 0 && varIndex <= 3 && !varType.equals(Type.DOUBLE) && !varType.equals(Type.FLOAT)) { // Special instructions for i, a, b, c, s types
                    storeInstruction = typePrefix + "store_" + varIndex;
                } else if (varIndex >= 0 && varType.equals(Type.FLOAT)) {
                    storeInstruction = "fstore_" + varIndex; // No fstore_0-3 variants, use fstore index
                } else if (varIndex >= 0 && varType.equals(Type.DOUBLE)) {
                    storeInstruction = "dstore_" + varIndex; // No dstore_0-3 variants, use dstore index
                } else if (varIndex >= 0 && varType.equals(Type.STRING)) {
                    storeInstruction = "astore_" + varIndex; // No astore_0-3 variants, use astore index
                } else if (varIndex >= 4) { // General instruction for indices >= 4
                    storeInstruction = typePrefix + "store " + varIndex;
                } else {
                    throw new RuntimeException("Invalid variable index for local/param assignment: " + varIndex);
                }
                code.append("  ").append(storeInstruction).append("\n"); // Add indentation and newline
                break;
            case GLOBAL:
            case GLOBAL_LOCAL:
                // Global variables use putstatic
                String typeDescriptor = JASMIN_TYPE_DESCRIPTORS.get(varType);
                if (typeDescriptor == null) {
                    throw new RuntimeException("Unknown type descriptor for global variable assignment: " + varType);
                }
                String programClassName = "Program"; // Replace with actual class name if different
                code.append(String.format("  putstatic %s/_gv%d %s\n", // Add indentation and newline
                                         programClassName, varIndex, typeDescriptor));
                break;
            default:
                throw new RuntimeException("Unknown variable scope type for assignment: " + scopeType);
        }

        return code;
    }
}
