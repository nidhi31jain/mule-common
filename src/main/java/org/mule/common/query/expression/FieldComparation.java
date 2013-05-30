package org.mule.common.query.expression;

import org.mule.common.query.Field;

/**
 * Expression for comparing
 *
 * @author Mulesoft, Inc
 */
public class FieldComparation extends Expression {

    public FieldComparation(BinaryOperator operator, Field field, Value value) {
        this.operator = operator;
        this.field = field;
        this.value = value;
    }

    /**
     * Field to be compared
     */
    private Field field;

    /**
     * Operator
     */
    private BinaryOperator operator;

    /**
     * Compare value
     */
    private Value value;

    public Field getField() {
        return field;
    }

    public BinaryOperator getOperator() {
        return operator;
    }

    public Value getValue() {
        return value;
    }

}
