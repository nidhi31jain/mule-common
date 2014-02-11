package org.mule.common.query.expression;

/**
 * an integer value
 */
public class IntegerValue extends Value<Integer>
{


    public IntegerValue(Integer value)
    {
        super(value);
    }


    public static IntegerValue fromLiteral(String literal)
    {
        return new IntegerValue(Integer.parseInt(literal));
    }
}
