package org.mule.common.query.expression;

/**
 * Represents an equal operator
 */
public class EqualsOperator implements BinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.equalsOperator();
    }
}
