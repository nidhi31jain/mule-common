package org.mule.common.query.expression;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Class for Binary logical expressions
 *
 * @author Mulesoft, Inc
 */
@XmlSeeAlso({
        And.class,
        Or.class
})
public abstract class BinaryLogicalExpression extends LogicalExpression {

    /**
     * Left expression
     */
    private Expression left;

    /**
     * Right expression
     */
    private Expression right;

    public Expression getLeft() {
        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
