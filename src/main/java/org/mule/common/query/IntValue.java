package org.mule.common.query;

import org.mule.common.query.expression.Value;

/**
 * an integer value
 */
public class IntValue extends Value {
    private int value;

    public IntValue(int value) {
        this.value = value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
