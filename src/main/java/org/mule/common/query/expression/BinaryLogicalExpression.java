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
    protected Expression left;

    /**
     * Right expression
     */
    protected Expression right;

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

}
