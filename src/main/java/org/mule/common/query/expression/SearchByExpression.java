/**
 *
 */
package org.mule.common.query.expression;

import org.mule.common.query.QueryVisitor;

public class SearchByExpression extends Expression
{

    private StringValue value;

    public SearchByExpression(StringValue value)
    {
        this.value = value;
    }


    public StringValue getValue()
    {
        return value;
    }

    @Override
    public void accept(QueryVisitor queryVisitor)
    {
        queryVisitor.visitSearchBy(getValue());
    }
}
