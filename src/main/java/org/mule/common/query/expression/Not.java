package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

/**
 * Class for representing NOT expression
 *
 * @author Mulesoft, Inc
 */
public class Not extends UnaryLogicalExpression {

    public Not(Expression expression){
        this.expression = expression;
    }

    @Override
    public void accept(QueryVisitor queryVisitor) {
        //TODO
    }
}
