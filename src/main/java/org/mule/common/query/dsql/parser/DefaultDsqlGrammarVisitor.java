package org.mule.common.query.dsql.parser;

import java.util.List;
import java.util.Stack;

import org.mule.common.query.DefaultQueryBuilder;
import org.mule.common.query.Field;
import org.mule.common.query.QueryBuilder;
import org.mule.common.query.Type;
import org.mule.common.query.dsql.grammar.DsqlParser;
import org.mule.common.query.expression.And;
import org.mule.common.query.expression.BinaryOperator;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.Expression;
import org.mule.common.query.expression.FieldComparation;
import org.mule.common.query.expression.GreaterOperator;
import org.mule.common.query.expression.LessOperator;
import org.mule.common.query.expression.Not;
import org.mule.common.query.expression.Or;
import org.mule.common.query.expression.Value;
import org.mule.common.query.expression.StringValue;

@SuppressWarnings("unchecked")
public class DefaultDsqlGrammarVisitor implements DsqlGrammarVisitor {

	private QueryBuilder queryBuilder;
	private Stack<Expression> expressions = new Stack<Expression>();
	private int expressionLevel;

	public DefaultDsqlGrammarVisitor() {
		queryBuilder = new DefaultQueryBuilder();
		expressionLevel = 0;
	}

	@Override
	public void visit(DsqlNode dsqlNode) {
		// Too generic. Empty on purpose.
	}

	@Override
	public void visit(SelectDsqlNode selectDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)selectDsqlNode.getChildren();
		
		for (final DsqlNode dsqlNode : children) {
			if (dsqlNode.getType() != DsqlParser.IDENT && 
					dsqlNode.getType() != DsqlParser.ASTERIX) {
				dsqlNode.accept(this);
			} else {
				queryBuilder.addField(new Field(dsqlNode.getText(), "string"));
			}
		}
	}

	@Override
	public void visit(FromDsqlNode fromDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)fromDsqlNode.getChildren();
		
		for (final DsqlNode dsqlNode : children) {
			queryBuilder.addType(new Type(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(ExpressionDsqlNode expressionDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)expressionDsqlNode.getChildren();
		 
		for (final DsqlNode dsqlNode : children) {
			int type = dsqlNode.getType();
			if (type == DsqlParser.AND || type == DsqlParser.OR || type == DsqlParser.NOT) {
				dsqlNode.accept(this);
			} else if (type == DsqlParser.OPERATOR) {
				List<DsqlNode> operatorChildren = (List<DsqlNode>)dsqlNode.getChildren();
				Field field = new Field(operatorChildren.get(0).getText());
				Value value = new StringValue(operatorChildren.get(1).getText());
				FieldComparation expression = new FieldComparation(getOperatorFor(dsqlNode.getText()), field, value);
				queryBuilder.setFilterExpression(expression);
			}
		}
	}

	@Override
	public void visit(AndDsqlNode andDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)andDsqlNode.getChildren();
		expressionLevel++;
		for (DsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
		expressionLevel--;
		Expression rightExpression = expressions.pop();
		Expression leftExpression = expressions.pop();
		And andExpression = new And(leftExpression, rightExpression);
		putExpression(andExpression);
	}

	private void putExpression(Expression expression) {
		if (expressionLevel == 0) {
			queryBuilder.setFilterExpression(expression);
		} else {
			expressions.push(expression);
		}
	}

	@Override
	public void visit(OrDsqlNode orDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)orDsqlNode.getChildren();
		expressionLevel++;
		for (DsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
		expressionLevel--;
		Expression rightExpression = expressions.pop();
		Expression leftExpression = expressions.pop();
		putExpression(new Or(leftExpression, rightExpression));
	}

	@Override
	public void visit(NotDsqlNode notDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)notDsqlNode.getChildren();
		expressionLevel++;
		for (DsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
		expressionLevel--;
		Expression expression = expressions.pop();
		putExpression(new Not(expression));
	}

	@Override
	public void visit(OperatorDsqlNode operatorDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)operatorDsqlNode.getChildren();
		Field field = new Field(children.get(0).getText());
		Value value = new StringValue(children.get(1).getText());
		expressions.push(new FieldComparation(getOperatorFor(operatorDsqlNode.getText()), field, value));
	}
	
	@Override
	public void visit(OpeningParenthesesDsqlNode openingParenthesesDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)openingParenthesesDsqlNode.getChildren();

		for (DsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
	}
	
	private BinaryOperator getOperatorFor(String text) {
		// TODO MAKE A FACTORY!
		
		if (text.equalsIgnoreCase("=")) {
			return new EqualsOperator();
		} else if (text.equalsIgnoreCase(">")) {
			return new GreaterOperator();
		} else if (text.equalsIgnoreCase("<")) {
			return new LessOperator();
		}
		return null;
	}

	@Override
	public void visit(OrderByDsqlNode orderByDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)orderByDsqlNode.getChildren();
		
		for (final DsqlNode dsqlNode : children) {
			queryBuilder.addOrderByField(new Field(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(LimitDsqlNode limitDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)limitDsqlNode.getChildren();
		
		for (final DsqlNode dsqlNode : children) {
			queryBuilder.setLimit(Integer.parseInt(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(OffsetDsqlNode offsetDsqlNode) {
		List<DsqlNode> children = (List<DsqlNode>)offsetDsqlNode.getChildren();
		
		for (final DsqlNode dsqlNode : children) {
			queryBuilder.setOffset(Integer.parseInt(dsqlNode.getText()));
		}
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}
}
