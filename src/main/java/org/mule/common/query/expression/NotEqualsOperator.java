package org.mule.common.query.expression;

/**
 * Represents an not equals operator
 */

public class NotEqualsOperator implements BinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.notEqualsOperator();
    }
}
