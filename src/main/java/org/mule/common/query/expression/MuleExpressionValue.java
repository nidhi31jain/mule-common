/**
 *
 */
package org.mule.common.query.expression;

public class MuleExpressionValue extends Value<String>
{

    protected MuleExpressionValue(String value)
    {
        super(value);
    }

    public static MuleExpressionValue fromLiteral(String value)
    {
        return new MuleExpressionValue(value);
    }
}
