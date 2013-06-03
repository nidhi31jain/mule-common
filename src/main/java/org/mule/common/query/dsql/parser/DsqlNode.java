package org.mule.common.query.dsql.parser;

import org.antlr.runtime.Token;
import org.antlr.runtime.tree.CommonTree;

public class DsqlNode 
	extends CommonTree {
	
	public DsqlNode(Token t) {
		super (t);
	}
	
	public void accept(DsqlGrammarVisitor visitor) {
		visitor.visit(this);
	}		
}