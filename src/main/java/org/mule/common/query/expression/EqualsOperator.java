package org.mule.common.query.expression;

/**
 * Represents an equal operator
 */
public class EqualsOperator extends AbstractBinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.equalsOperator();
    }
}
