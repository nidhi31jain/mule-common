package org.mule.common.query.expression;

/**
 * Class for representing query expressions which contains functions.
 * E.g.: ...WHERE MAX(field)
 *
 * @author Mulesoft, Inc
 */
public class FunctionExpression extends Expression {

    /**
     * Function expression
     */
    private String function;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
}
