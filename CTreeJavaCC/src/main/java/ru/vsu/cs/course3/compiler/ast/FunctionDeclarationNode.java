package ru.vsu.cs.course3.compiler.ast;

import java.util.Collections;
import java.util.List;


public class FunctionDeclarationNode implements StmtNode {
    private TypeNode type;
    private IdentNode name;
    private List<FunctionParamDeclarationNode> params;
    private StmtNode body;

    public FunctionDeclarationNode(TypeNode type, IdentNode name, List<FunctionParamDeclarationNode> params, StmtNode body) {
        this.type = type;
        this.name = name;
        this.params = params;
        this.body = body;
    }

    @Override
    public String toString() {
        return "function";
    }

    @Override
    public List<? extends AstNode> childs() {
        return Collections.singletonList(body);
//        List<AstNode> astNodes = new ArrayList<>();
//        List<AstNode> groupNodes = new ArrayList<>();
//        groupNodes.add(this.name);
//        astNodes.add(new GroupNode(this.type.toString(), groupNodes));
//        astNodes.add(new GroupNode("params", new ArrayList<>(this.params)));
//        astNodes.add(this.body);
//        return astNodes;
    }

    public TypeNode getType() {
        return type;
    }

    public void setType(TypeNode type) {
        this.type = type;
    }

    public IdentNode getName() {
        return name;
    }

    public void setName(IdentNode name) {
        this.name = name;
    }

    public List<FunctionParamDeclarationNode> getParams() {
        return params;
    }

    public void setParams(List<FunctionParamDeclarationNode> params) {
        this.params = params;
    }

    public StmtNode getBody() {
        return body;
    }

    public void setBody(StmtListNode body) {
        this.body = body;
    }
}