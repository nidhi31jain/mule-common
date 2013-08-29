package org.mule.common.query;

import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.OperatorVisitor;
import org.mule.common.query.expression.Value;

import java.util.List;

/**
 * An interface to implement a query visitor
 */
public interface QueryVisitor {

    void visitFields(List<Field> fields);

    void visitTypes(List<Type> types);

    void visitOrderByFields(List<Field> orderByFields, Direction direction);

    void visitAnd();

    void visitOR();

    void visitComparison(String operator, Field field, Value value);

    OperatorVisitor operatorVisitor();

    void visitBeginExpression();

    public void visitInitPrecedence();

    public void visitEndPrecedence();

    void visitLimit(int limit);

    void visitOffset(int offset);

    /**
     *  This method simply acts a friendly reminder not to implement DsqlQuery visitor directly and instead extend DefaultQueryVisitor.
     */
    void _dont_implement_QueryVisitor___instead_extend_DefaultQueryVisitor();

}
