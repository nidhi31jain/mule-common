package org.mule.common.query.expression;

/**
 * Represents an less or equals operator
 */
public class LessOrEqualsOperator extends AbstractBinaryOperator{
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.lessOrEqualsOperator();
    }
}
