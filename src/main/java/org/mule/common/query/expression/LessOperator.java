package org.mule.common.query.expression;

/**
 * Represent a less operator
 */
public class LessOperator extends AbstractBinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.lessOperator();
    }
}
