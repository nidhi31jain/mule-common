/**
 *
 */
package org.mule.common.query.expression;

public class IdentifierValue extends Value<String>
{

    protected IdentifierValue(String value)
    {
        super(value);
    }

    public static IdentifierValue fromLiteral(String value)
    {
        if (value.startsWith("[") && value.endsWith("]"))
        {
            value =  value.substring(1, value.length() - 1);
        }
        return new IdentifierValue(value);
    }
}
