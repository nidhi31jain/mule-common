package org.mule.common.query.expression;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @author Mulesoft, Inc
 */
@XmlSeeAlso({
        UnaryLogicalExpression.class,
        BinaryLogicalExpression.class
})
public abstract class LogicalExpression extends Expression {

}
