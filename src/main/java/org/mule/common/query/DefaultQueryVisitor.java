package org.mule.common.query;

import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.OperatorVisitor;
import org.mule.common.query.expression.Value;

import java.util.List;

/**
 * Extend this class to create your own query visitor
 */

public abstract class DefaultQueryVisitor implements QueryVisitor {


    @Override
    public void visitFields(List<Field> fields) {

    }

    @Override
    public void visitTypes(List<Type> types) {

    }

    @Override
    public void visitAnd() {

    }

    @Override
    public void visitOR() {

    }

    @Override
    public void visitComparison(String operator, Field field, Value value) {

    }

    @Override
    public OperatorVisitor operatorVisitor() {
        return new DefaultOperatorVisitor();
    }

    @Override
    public void visitBeginExpression() {

    }

    @Override
    public void visitInitPrecedence() {

    }

    @Override
    public void visitEndPrecedence() {

    }

    @Override
    public void visitLimit(int limit) {

    }

    @Override
    public void visitOffset(int offset) {

    }

    @Override
    public void visitOrderByFields(List<Field> orderByFields, Direction direction) {

    }

    @Override
    public void _dont_implement_QueryVisitor___instead_extend_DefaultQueryVisitor() {
    }
}
