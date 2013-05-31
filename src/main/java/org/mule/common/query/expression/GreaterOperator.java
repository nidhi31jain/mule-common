package org.mule.common.query.expression;

/**
 * Represents a greater operator
 */
public class GreaterOperator implements BinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.greaterOperator();
    }
}
