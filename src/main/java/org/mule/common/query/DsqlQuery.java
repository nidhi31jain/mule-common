package org.mule.common.query;

import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.EmptyExpression;
import org.mule.common.query.expression.Expression;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the query model for Mule DsqlQuery Builder
 *
 * @author Mulesoft, Inc
 */
public class DsqlQuery implements Query{

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


    public DsqlQuery(){
        this.types = new ArrayList<Type>();
        this.fields = new ArrayList<Field>();
        this.orderByFields = new ArrayList<Field>();
        this.direction = null;
        this.filterExpression = new EmptyExpression();
        this.joinExpression = new EmptyExpression();
        this.limit = -1;
        this.offset = -1;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public List<Field> getOrderByFields() {
        return orderByFields;
    }

    @Override
    public Direction getDirection(){
        return direction;
    }

    @Override
    public Expression getFilterExpression() {
        return filterExpression;
    }


    @Override
    public List<Type> getTypes() {
        return types;
    }


    @Override
    public Expression getJoinExpression() {
        return joinExpression;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void addType(Type type) {
        this.types.add(type);
    }

    @Override
    public void addField(Field field) {
        this.fields.add(field);
    }

    @Override
    public void addOrderField(Field orderByField) {
        this.orderByFields.add(orderByField);
    }

    @Override
    public void setDirection(Direction direction){
        this.direction = direction;
    }

    @Override
    public void setFilterExpression(Expression filterExpression) {
        this.filterExpression = filterExpression;
    }

    @Override
    public void setJoinExpression(Expression joinExpression) {
        this.joinExpression = joinExpression;
    }

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
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
            if (! hasDirection()){
                queryVisitor.visitOrderByFields(this.orderByFields);
            }else{
                queryVisitor.visitOrderByFields(this.orderByFields, this.direction);
            }
        }

        if (limit != -1) {
            queryVisitor.visitLimit(this.limit);
        }

        if (offset != -1) {
            queryVisitor.visitOffset(this.offset);
        }
    }

    @Override
    public boolean hasDirection() {
        return this.direction != null;
    }
}
