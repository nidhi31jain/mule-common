package org.mule.common.query.expression;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Base class for Unary Expressions
 * @author Mulesoft, Inc
 */
@XmlSeeAlso({
        Not.class
})
public abstract class UnaryLogicalExpression extends LogicalExpression {

    /**
     * Right expression
     */
    private Expression right;

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }
}
