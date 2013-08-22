package org.mule.common.query;


import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.Expression;

/**
 * This represent a basic query
 */
public abstract class QueryBuilder {

    public abstract QueryBuilder addType(Type type);

    public abstract QueryBuilder addField(Field field);

    public abstract QueryBuilder addOrderByField(Field field);

    public abstract QueryBuilder setDirection(Direction direction);
    
    public abstract QueryBuilder setFilterExpression(Expression expression);

    public abstract QueryBuilder setJoinExpression(Expression joinExpression);

    public abstract QueryBuilder setLimit(int limit);

    public abstract QueryBuilder setOffset(int offset);

    public abstract DsqlQuery build() throws QueryBuilderException;

}
