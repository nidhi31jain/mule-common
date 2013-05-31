package org.mule.common.query.expression;

/**
 * Represents an less or equals operator
 */
public class LessOrEqualsOperator implements BinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.lessOrEqualsOperator();
    }
}
