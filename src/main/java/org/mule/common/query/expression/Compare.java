package org.mule.common.query.expression;

import org.mule.common.query.Field;

/**
 * Expression for comparing
 *
 * @author Mulesoft, Inc
 */
public class CompareExpression extends Expression {

    /**
     * Field to be compared
     */
    private Field field;

    /**
     * Operator
     */
    private String operator;

    /**
     * Compare value
     */
    private String value;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
