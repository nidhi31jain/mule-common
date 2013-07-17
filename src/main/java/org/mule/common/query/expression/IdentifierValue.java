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

    public static IdentifierValue fromLiteral(String value){
         return new IdentifierValue(value);
    }
}
