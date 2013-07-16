/**
 *
 */
package org.mule.common.query.expression;

public class DateValue extends Value<String>
{

    public DateValue(String value)
    {
        super(value);
    }

    public static DateValue fromLiteral(String literal){
        return new DateValue(literal);
    }
}
