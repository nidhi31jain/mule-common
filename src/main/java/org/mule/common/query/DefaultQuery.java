package org.mule.common.query;

import org.mule.common.query.expression.EmptyExpression;
import org.mule.common.query.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic query
 */
public class DefaultQuery extends Query {

    public DefaultQuery(){
        this.types = new ArrayList<Type>();
        this.fields = new ArrayList<Field>();
        this.orderByFields = new ArrayList<Field>();
        this.filterExpression = new EmptyExpression();
        this.joinExpression = new EmptyExpression();
        this.limit = -1;
        this.offset = -1;
    }

    public void addType(Type type) {
        this.types.add(type);
    }

    public void addField(Field field) {
        this.fields.add(field);
    }

    public void addOrderField(Field orderByField) {
        this.orderByFields.add(orderByField);
    }

    public void setFilterExpression(Expression filterExpression) {
        this.filterExpression = filterExpression;
    }

    public void setJoinExpression(Expression joinExpression) {
        this.joinExpression = joinExpression;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
