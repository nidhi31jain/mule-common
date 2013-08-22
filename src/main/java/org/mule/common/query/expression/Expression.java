package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Expressions for Mule DsqlQuery Builder.
 *
 * @author Mulesoft, Inc
 */
@XmlSeeAlso({
        FieldComparation.class,
        LogicalExpression.class,
        Function.class
})
public abstract class Expression {

    public abstract void accept(QueryVisitor queryVisitor);
}
