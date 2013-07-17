/**
 *
 */
package org.mule.common.query.expression;

public class UnknownValue extends Value<String>
{

    protected UnknownValue(String value)
    {
        super(value);
    }

    public static UnknownValue fromLiteral(String value)
    {
        return new UnknownValue(value);
    }
}
