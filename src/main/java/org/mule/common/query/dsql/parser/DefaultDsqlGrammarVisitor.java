package org.mule.common.query.dsql.parser;

import org.mule.common.query.DefaultQueryBuilder;
import org.mule.common.query.Field;
import org.mule.common.query.QueryBuilder;
import org.mule.common.query.Type;
import org.mule.common.query.dsql.grammar.DsqlParser;
import org.mule.common.query.expression.And;
import org.mule.common.query.expression.BinaryOperator;
import org.mule.common.query.expression.BooleanValue;
import org.mule.common.query.expression.Expression;
import org.mule.common.query.expression.FieldComparation;
import org.mule.common.query.expression.NumberValue;
import org.mule.common.query.expression.Not;
import org.mule.common.query.expression.Or;
import org.mule.common.query.expression.StringValue;
import org.mule.common.query.expression.Value;

import java.util.List;
import java.util.Stack;

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
		List<IDsqlNode> children = selectDsqlNode.getChildren();
		
		for (final IDsqlNode dsqlNode : children) {
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
		List<IDsqlNode> children = fromDsqlNode.getChildren();
		
		for (final IDsqlNode dsqlNode : children) {
			queryBuilder.addType(new Type(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(ExpressionDsqlNode expressionDsqlNode) {
		List<IDsqlNode> children = expressionDsqlNode.getChildren();
		 
		for (final IDsqlNode dsqlNode : children) {
			int type = dsqlNode.getType();
			if (type == DsqlParser.AND || type == DsqlParser.OR || type == DsqlParser.NOT) {
				dsqlNode.accept(this);
			} else if (type == DsqlParser.OPERATOR| type == DsqlParser.COMPARATOR) {
				List<IDsqlNode> operatorChildren = dsqlNode.getChildren();
				Field field = new Field(operatorChildren.get(0).getText());
                IDsqlNode node = operatorChildren.get(1);
                Value value;
                switch (node.getType()){
                    case DsqlParser.NUMBER_LITERAL:
                        value = new NumberValue(Double.parseDouble(node.getText()));
                        break;
                    case DsqlParser.BOOLEAN_LITERAL:
                        value = new BooleanValue(Boolean.parseBoolean(node.getText()));
                        break;
                    default:
                        value = new StringValue(node.getText());
                        break;
                }

                FieldComparation expression = new FieldComparation(getOperatorFor(dsqlNode.getText()), field, value);
				queryBuilder.setFilterExpression(expression);
			} else if (type == DsqlParser.OPENING_PARENTHESIS) {
				dsqlNode.accept(this);
			}
		}
	}

	@Override
	public void visit(AndDsqlNode andDsqlNode) {
		List<IDsqlNode> children = andDsqlNode.getChildren();
		expressionLevel++;
		for (IDsqlNode dsqlNode : children) {
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
		List<IDsqlNode> children = orDsqlNode.getChildren();
		expressionLevel++;
		for (IDsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
		expressionLevel--;
		Expression rightExpression = expressions.pop();
		Expression leftExpression = expressions.pop();
		putExpression(new Or(leftExpression, rightExpression));
	}

	@Override
	public void visit(NotDsqlNode notDsqlNode) {
		List<IDsqlNode> children = notDsqlNode.getChildren();
		expressionLevel++;
		for (IDsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
		expressionLevel--;
		Expression expression = expressions.pop();
		putExpression(new Not(expression));
	}

	@Override
	public void visit(OperatorDsqlNode operatorDsqlNode) {
		List<IDsqlNode> children = operatorDsqlNode.getChildren();
		Field field = new Field(children.get(0).getText());
		Value value = new StringValue(children.get(1).getText());
		expressions.push(new FieldComparation(getOperatorFor(operatorDsqlNode.getText()), field, value));
	}
	
	@Override
	public void visit(OpeningParenthesesDsqlNode openingParenthesesDsqlNode) {
		List<IDsqlNode> children = openingParenthesesDsqlNode.getChildren();

		for (IDsqlNode dsqlNode : children) {
			dsqlNode.accept(this);
		}
	}
	
	private BinaryOperator getOperatorFor(String symbol) {
		// TODO: refactor this when we start using unary operators.
		return (BinaryOperator)QueryModelOperatorFactory.getInstance().getOperator(symbol);
	}

	@Override
	public void visit(OrderByDsqlNode orderByDsqlNode) {
		List<IDsqlNode> children = orderByDsqlNode.getChildren();
		
		for (final IDsqlNode dsqlNode : children) {
			queryBuilder.addOrderByField(new Field(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(LimitDsqlNode limitDsqlNode) {
		List<IDsqlNode> children = limitDsqlNode.getChildren();
		
		for (final IDsqlNode dsqlNode : children) {
			queryBuilder.setLimit(Integer.parseInt(dsqlNode.getText()));
		}
	}

	@Override
	public void visit(OffsetDsqlNode offsetDsqlNode) {
		List<IDsqlNode> children = offsetDsqlNode.getChildren();
		
		for (final IDsqlNode dsqlNode : children) {
			queryBuilder.setOffset(Integer.parseInt(dsqlNode.getText()));
		}
	}

	public QueryBuilder getQueryBuilder() {
		return queryBuilder;
	}
}
