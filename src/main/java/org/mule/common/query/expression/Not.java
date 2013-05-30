package org.mule.common.query.expression;

/**
 * Class for representing NOT expression
 *
 * @author Mulesoft, Inc
 */
public class Not extends UnaryLogicalExpression {

    public Not(Expression expression){
        this.expression = expression;
        this.operator = UnaryOperator.NOT;
    }

}
