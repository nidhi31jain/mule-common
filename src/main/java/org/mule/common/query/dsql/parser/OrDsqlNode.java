package org.mule.common.query.dsql.parser;

import org.antlr.runtime.Token;

public class OrDsqlNode extends DsqlNode {

	public OrDsqlNode(Token t) {
		super(t);
	}

	@Override
	public void accept(DsqlGrammarVisitor visitor) {
		visitor.visit(this);
	}
}
