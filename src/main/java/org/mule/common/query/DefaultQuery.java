package org.mule.common.query;

import org.mule.common.query.expression.EmptyExpression;
import org.mule.common.query.expression.Expression;

import java.util.ArrayList;

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

    @Override
    public void accept(QueryVisitor queryVisitor) {
    	// This order matters! Please don't change it. Visit types first, then fields.
    	queryVisitor.visitTypes(this.types);
        queryVisitor.visitFields(this.fields);
        if (!(this.filterExpression instanceof EmptyExpression)) {
            queryVisitor.visitBeginExpression();
            this.filterExpression.accept(queryVisitor);
        }

        if (this.orderByFields.size()>0){
            queryVisitor.visitOrderByFields(this.orderByFields);
        }

        if (limit != -1) {
            queryVisitor.visitLimit(this.limit);
        }

        if (offset != -1) {
            queryVisitor.visitOffset(this.offset);
        }
    }
}
