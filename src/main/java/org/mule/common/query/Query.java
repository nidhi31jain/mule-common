package org.mule.common.query;

import org.mule.common.query.expression.Expression;

import java.util.List;

/**
 * Represents the query model for Mule Query Builder
 *
 * @author Mulesoft, Inc
 */
public abstract class Query {

    /**
     * Type or types to be queried
     */
    private List<Type> types;

    /**
     * Fields to be retrieved
     */
    private List<Field> fields;

    /**
     * Fields for sorting the query
     */
    private List<Field> orderByFields;

    /**
     * Expression which contains the filter conditions
     */
    private Expression filterExpression;

    /**
     * Expression for joining the different types
     */
    private Expression joinExpression;


    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getOrderByFields() {
        return orderByFields;
    }

    public void setOrderByFields(List<Field> orderByFields) {
        this.orderByFields = orderByFields;
    }

    public Expression getFilterExpression() {
        return filterExpression;
    }

    public void setFilterExpression(Expression filterExpression) {
        this.filterExpression = filterExpression;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    public Expression getJoinExpression() {
        return joinExpression;
    }

    public void setJoinExpression(Expression joinExpression) {
        this.joinExpression = joinExpression;
    }
}
