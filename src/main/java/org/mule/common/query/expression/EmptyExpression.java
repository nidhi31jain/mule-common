package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

/**
 * Represents an empty expression
 */

public class EmptyExpression extends Expression {
    @Override
    public void accept(QueryVisitor queryVisitor) {
        //DO NOTHING ON EMPTY CASE
    }
}
