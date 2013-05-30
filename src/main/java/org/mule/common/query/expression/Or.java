package org.mule.common.query.expression;

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

}
