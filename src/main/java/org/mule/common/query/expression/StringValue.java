package org.mule.common.query.expression;

/**
 * Represents an string value
 */
public class StringValue extends Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }
    
    public String toString() {
        return value;
    }
}
