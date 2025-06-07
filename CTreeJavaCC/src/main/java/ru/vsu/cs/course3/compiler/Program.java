package ru.vsu.cs.course3.compiler;

import ru.vsu.cs.course3.compiler.ast.StmtListNode;
import ru.vsu.cs.course3.compiler.semantic.GlobalScope;
import ru.vsu.cs.course3.compiler.semantic.Function;
import ru.vsu.cs.course3.compiler.semantic.Variable;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class Program {
    public static void main(String[] args) throws Exception {
        Reader input = args.length > 0 ? new FileReader(args[0]) : new InputStreamReader(System.in);
        Parser parser = new Parser(input);
        StmtListNode program = parser.program();
        GlobalScope scope = new GlobalScope();
        program.initialize(scope);
        program.semanticCheck();
        System.out.println("Semantic check completed successfully");
        StringBuilder code = new StringBuilder();
        code.append(".class public ").append("program").append("\n");
        code.append(".super java/lang/Object\n");

        for (Variable var : scope.getVariables()) {
            code.append(".field static ")
                .append(var.getName())
                .append(" ")
                .append(var.getType().getAbbreviation())
                .append(" = ")
                .append("0")
                .append("\n");
        }

        // Add constructor
        code.append("""
            .method public <init>()V
               aload_0
               invokenonvirtual java/lang/Object/<init>()V
               return
            .end method
            """);
        code.append(".method public static main([Ljava/lang/String;)V\n");
        code.append(".limit stack 20\n");
        code.append(".limit locals ").append(scope.getTotalLocals()).append("\n");
        code.append(program.generateCode());
        code.append("return\n");
        code.append(".end method");

        // Output the generated code
        System.out.println(code.toString());
    }
}
