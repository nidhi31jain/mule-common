package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

/**
 * Class for representing AND logical condition
 *
 * @author Mulesoft, Inc
 */
public class And extends BinaryLogicalExpression {

    public And(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(QueryVisitor queryVisitor) {
        queryVisitor.visitInitPrecedence();
        left.accept(queryVisitor);
        queryVisitor.visitAnd();
        right.accept(queryVisitor);
        queryVisitor.visitEndPrecedence();
    }
}
