package org.mule.common.query.dsql.parser;

import java.util.HashMap;

import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.GreaterOperator;
import org.mule.common.query.expression.GreaterOrEqualsOperator;
import org.mule.common.query.expression.LessOperator;
import org.mule.common.query.expression.LessOrEqualsOperator;
import org.mule.common.query.expression.NotEqualsOperator;
import org.mule.common.query.expression.Operator;

public final class QueryModelOperatorFactory {

	private static QueryModelOperatorFactory instance = new QueryModelOperatorFactory();
	
	private HashMap<String, Operator> operators = new HashMap<String, Operator>();
	
	private QueryModelOperatorFactory() {
		operators.put("=", new EqualsOperator());
		operators.put(">", new GreaterOperator());
		operators.put("<", new LessOperator());
		operators.put(">=", new GreaterOrEqualsOperator());
		operators.put("<=", new LessOrEqualsOperator());
		operators.put("<>", new NotEqualsOperator());
		//operators.put("!", new NotOperator());
	}
	
	public static QueryModelOperatorFactory getInstance() {
		return instance;
	}
	
	public Operator getOperator(String symbol) {
		return operators.get(symbol);
	}
}
