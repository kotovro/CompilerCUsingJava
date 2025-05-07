package ru.vsu.cs.course3.compiler.ast;

import ru.vsu.cs.course3.compiler.semantic.Scope;

import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class BasicNode implements AstNode{

    protected Scope scope;

    Collection<? extends AstNode> childs() {
        return null;
    }


    public Collection<String> tree() {
        // крайне неэффективно, но относительно просто
        List<String> res = new LinkedList<>();
        res.add(toString());
        Collection<? extends AstNode> childs = childs();
        if (childs != null) {
            int i = 0;
            for (AstNode child : childs) {
                String ch0 = "├", ch = "│";
                if (i++ == childs.size() - 1) {
                    ch0 = "└"; ch = " ";
                }
                Collection<String> tree = child.tree();
                if (tree == null) {
                    continue;
                }
                int j = 0;
                for (String s : tree) {
                    res.add((j++ == 0 ? ch0 : ch) + ' ' + s);
                }
            }
        }
        return res;
    }

     public void printTree(PrintStream printStream) {
        Collection<String> tree = tree();
        if (tree == null) {
            return;
        }
        for (String s : tree) {
            printStream.println(s);
        }
    }
}
