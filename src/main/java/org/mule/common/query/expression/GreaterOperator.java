package org.mule.common.query.expression;

/**
 * Represents a greater operator
 */
public class GreaterOperator extends AbstractBinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.greaterOperator();
    }
}
