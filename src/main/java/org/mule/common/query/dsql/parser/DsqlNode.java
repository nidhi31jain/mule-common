package org.mule.common.query.dsql.parser;

import java.util.List;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class DsqlNode 
	extends CommonTree
	implements IDsqlNode {
	
	public DsqlNode(Token t) {
		super (t);
	}
	
	@Override
	public void accept(DsqlGrammarVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<IDsqlNode> getChildren() {
		return (List<IDsqlNode>) super.getChildren();
	}
}