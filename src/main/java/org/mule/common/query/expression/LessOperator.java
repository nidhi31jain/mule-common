package org.mule.common.query.expression;

import org.mule.common.query.DefaultOperatorVisitor;

/**
 * Represent a less operator
 */
public class LessOperator extends AbstractBinaryOperator {
    @Override
    public String accept(OperatorVisitor operatorVisitor) {
        return operatorVisitor.lessOperator();
    }
}
