package org.mule.common.query;

import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.OperatorVisitor;
import org.mule.common.query.expression.Value;

import java.util.List;

/**
 * <p><strong>This is used for translating DSQL to your native query language</strong></p>
 *
 * <p>Extend this class to create your own query visitor. This is useful to walk the {@link DsqlQuery} structure and translate it to a native one without doing such a mess.</p>
 *
 * <p>The main idea behind this visitor is to build your native query incrementally on each visit method accordingly. For a practical example look at {@link DsqlQueryVisitor}.</p>
 *
 * <p>For the <strong>DSQL</strong> operators translation there's other visitor {@link DefaultOperatorVisitor} you must extend.</p>
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
    public void visitComparison(String operator, Field field, Value<?> value) {

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
