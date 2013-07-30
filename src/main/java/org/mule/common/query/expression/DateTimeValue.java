/**
 *
 */
package org.mule.common.query.expression;

public class DateTimeValue extends Value<String>
{

    public DateTimeValue(String value)
    {
        super(value);
    }

    public static DateTimeValue fromLiteral(String literal){
        return new DateTimeValue(literal);
    }
}
