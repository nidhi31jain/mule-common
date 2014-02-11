package org.mule.common.query.expression;

/**
 * an integer value
 */
public class NumberValue extends Value<Double>
{


    public NumberValue(Double value)
    {
        super(value);
    }


    public static NumberValue fromLiteral(String literal)
    {
        return new NumberValue(Double.parseDouble(literal));
    }
}
