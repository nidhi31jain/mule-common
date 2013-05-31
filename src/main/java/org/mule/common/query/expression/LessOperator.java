package org.mule.common.query.expression;

/**
 * Represent a less operator
 */
public class LessOperator implements BinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.lessOperator();
    }
}
