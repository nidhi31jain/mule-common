package org.mule.common.query;

import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.Expression;

import java.util.List;

@Deprecated
public interface Query {

   public void accept(QueryVisitor queryVisitor);

    List<Field> getFields();

    List<Field> getOrderByFields();

    Direction getDirection();

    Expression getFilterExpression();

    List<Type> getTypes();

    Expression getJoinExpression();

    int getLimit();

    int getOffset();

    void addType(Type type);

    void addField(Field field);

    void addOrderField(Field orderByField);

    void setDirection(Direction direction);

    void setFilterExpression(Expression filterExpression);

    void setJoinExpression(Expression joinExpression);

    void setLimit(int limit);

    void setOffset(int offset);

    boolean hasDirection();
}
