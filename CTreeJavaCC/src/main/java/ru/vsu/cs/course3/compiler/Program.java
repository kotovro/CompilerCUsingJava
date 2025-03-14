package ru.vsu.cs.course3.compiler;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

import ru.vsu.cs.course3.compiler.ast.AstNode;
//import ru.vsu.cs.course3.compiler.Parser;


public class Program {
    public static void main(String[] args) throws Exception {
        Reader input = args.length > 0 ? new FileReader(args[0]) : new InputStreamReader(System.in);

//        AstNode result = parser.start();
//        result.printTree(System.out);
    }
}
