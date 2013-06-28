package org.mule.common.query;

import java.util.Iterator;
import java.util.List;

import org.mule.common.query.expression.OperatorVisitor;
import org.mule.common.query.expression.Value;

/**
 * DsqlQueryVisitor
 */
public class DsqlQueryVisitor extends DefaultQueryVisitor {

    private StringBuilder stringBuilder;

    public DsqlQueryVisitor() {
       stringBuilder = new StringBuilder();
    }

    @Override
    public void visitFields(List<Field> fields) {
    	StringBuilder select = new StringBuilder();
    	select.append("SELECT ");
        Iterator<Field> fieldIterable = fields.iterator();
        while (fieldIterable.hasNext()) {
            select.append(fieldIterable.next().getName());
            if (fieldIterable.hasNext()) {
            	select.append(",");
            }
        }
        
        stringBuilder.insert(0, select);
    }

    @Override
    public void visitTypes(List<Type> types) {
        stringBuilder.append(" FROM ");
        Iterator<Type> typeIterator = types.iterator();
        while (typeIterator.hasNext()){
            stringBuilder.append(typeIterator.next().getName());
            if (typeIterator.hasNext()) {
                stringBuilder.append(",");
            }
        }
    }

    @Override
    public void visitOrderByFields(List<Field> orderByFields) {
        stringBuilder.append(" ORDER BY ");
        Iterator<Field> orderByFieldsIterator = orderByFields.iterator();
        while (orderByFieldsIterator.hasNext()){
            stringBuilder.append(orderByFieldsIterator.next().getName());
            if (orderByFieldsIterator.hasNext()){
                stringBuilder.append(",");
            }
        }
    }

    @Override
    public void visitBeginExpression() {
        stringBuilder.append(" WHERE ");
    }

    @Override
    public void visitInitPrecedence() {
        stringBuilder.append("(");
    }

    @Override
    public void visitEndPrecedence() {
        stringBuilder.append(")");
    }

    @Override
    public void visitLimit(int limit) {
        stringBuilder.append(" LIMIT ").append(limit);
    }

    @Override
    public void visitOffset(int offset) {
        stringBuilder.append(" OFFSET ").append(offset);
    }

    @Override
    public void visitAnd() {
        stringBuilder.append(" AND ");
    }


    @Override
    public void visitOR() {
        stringBuilder.append(" OR ");
    }

    @Override
    public void visitComparison(String operator, Field field, Value value) {
        stringBuilder.append(field.getName()).append(operator).append(value.toString());
    }

    @Override
    public OperatorVisitor operatorVisitor() {
        return new OperatorVisitor() {
            @Override
            public String lessOperator() {
                return " < ";
            }

            @Override
            public String greaterOperator() {
                return " > ";
            }

            @Override
            public String lessOrEqualsOperator() {
                return " <= ";
            }

            @Override
            public String equalsOperator() {
                return " = ";
            }

            @Override
            public String notEqualsOperator() {
                return " <> ";
            }

            @Override
            public String greaterOrEqualsOperator() {
                return " >= ";
            }
        };
    }

    public String dsqlQuery() {
        return stringBuilder.toString();
    }
}
