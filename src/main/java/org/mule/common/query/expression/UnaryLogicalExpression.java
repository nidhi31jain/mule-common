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
     *  expression
     */
    protected Expression expression;

    public Expression getRight() {
        return expression;
    }
}
