package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

/**
 * Logical Expression OR
 *
 * @author Mulesoft, Inc
 */
public class Or extends BinaryLogicalExpression {

    public Or(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(QueryVisitor queryVisitor) {
        queryVisitor.visitInitPrecedence();
        left.accept(queryVisitor);
        queryVisitor.visitOR();
        right.accept(queryVisitor);
        queryVisitor.visitEndPrecedence();
    }
}
