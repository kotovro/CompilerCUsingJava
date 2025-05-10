package ru.vsu.cs.course3.compiler;

import ru.vsu.cs.course3.compiler.ast.StmtListNode;
import ru.vsu.cs.course3.compiler.semantic.GlobalScope;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;


public class Program {
    public static void main(String[] args) throws Exception {

        Reader input = args.length > 0 ? new FileReader(args[0]) : new InputStreamReader(System.in);
        Parser parser = new Parser(input);

        StmtListNode program = parser.program();
        program.initialize(new GlobalScope());
        program.semanticCheck();
        System.out.println("Ended semantic check");
//        Parser parser = new Parser(input);
//        BasicNode result = parser.start();
//        result.printTree(System.out);
    }
}
