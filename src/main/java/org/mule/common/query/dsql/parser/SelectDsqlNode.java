package org.mule.common.query.dsql.parser;

import org.antlr.runtime.Token;

public class SelectDsqlNode extends DsqlNode {

	public SelectDsqlNode(Token t) {
		super(t);
	}

	@Override
	public void accept(DsqlGrammarVisitor visitor) {
		visitor.visit(this);
	}

}
