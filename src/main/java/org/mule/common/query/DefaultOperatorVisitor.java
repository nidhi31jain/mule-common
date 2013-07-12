package org.mule.common.query;

import org.mule.common.query.expression.OperatorVisitor;

public class DefaultOperatorVisitor implements OperatorVisitor {
	@Override
	public String lessOperator() {
		return " < ";
	}

	@Override
	public String greaterOperator() {
		return " > ";
	}

	@Override
	public String lessOrEqualsOperator() {
		return " <= ";
	}

	@Override
	public String equalsOperator() {
		return " = ";
	}

	@Override
	public String notEqualsOperator() {
		return " <> ";
	}

	@Override
	public String greaterOrEqualsOperator() {
		return " >= ";
	}

    @Override
    public String likeOperator()
    {
        return " like ";
    }
}
