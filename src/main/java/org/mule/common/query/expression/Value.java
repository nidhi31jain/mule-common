package org.mule.common.query.expression;

/**
 * This represent a value for a query field comparation
 */
public abstract class Value<T>
{

    private T value;

    protected Value(T value)
    {
        this.value = value;
    }

    public T getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
