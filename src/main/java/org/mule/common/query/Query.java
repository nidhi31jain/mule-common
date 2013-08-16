package org.mule.common.query;

import org.mule.common.query.expression.Direction;
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
    protected List<Type> types;

    /**
     * Fields to be retrieved
     */
    protected List<Field> fields;

    /**
     * Fields for sorting the query
     */
    protected List<Field> orderByFields;

    /**
     * Direction to determine the ascending or descending sorting
     */
    protected Direction direction;

    /**
     * Expression which contains the filter conditions
     */
    protected Expression filterExpression;

    /**
     * Expression for joining the different types
     */
    protected Expression joinExpression;

    protected int limit;

    protected int offset;


    public List<Field> getFields() {
        return fields;
    }

    public List<Field> getOrderByFields() {
        return orderByFields;
    }

    public Direction getDirection(){
        return direction;
    }

    public Expression getFilterExpression() {
        return filterExpression;
    }


    public List<Type> getTypes() {
        return types;
    }


    public Expression getJoinExpression() {
        return joinExpression;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public abstract void accept(QueryVisitor queryVisitor);
}
