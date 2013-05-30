package org.mule.common.query.expression;

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

}
