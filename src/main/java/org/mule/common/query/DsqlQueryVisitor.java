package org.mule.common.query;

import org.mule.common.query.expression.*;

import java.util.Iterator;
import java.util.List;

/**
 * DsqlQueryVisitor
 */
public class DsqlQueryVisitor extends DefaultQueryVisitor
{

    private StringBuilder stringBuilder;

    public DsqlQueryVisitor()
    {
        stringBuilder = new StringBuilder();
    }

    @Override
    public void visitFields(List<Field> fields)
    {
        StringBuilder select = new StringBuilder();
        select.append("SELECT ");
        Iterator<Field> fieldIterable = fields.iterator();
        while (fieldIterable.hasNext())
        {
            String fieldName = fieldIterable.next().getName();
            if(fieldName.contains(" ")){
            	fieldName = "'" + fieldName + "'";
            }
			select.append(fieldName);
            if (fieldIterable.hasNext())
            {
                select.append(",");
            }
        }

        stringBuilder.insert(0, select);
    }

    @Override
    public void visitTypes(List<Type> types)
    {
        stringBuilder.append(" FROM ");
        Iterator<Type> typeIterator = types.iterator();
        while (typeIterator.hasNext())
        {
            stringBuilder.append(typeIterator.next().getName());
            if (typeIterator.hasNext())
            {
                stringBuilder.append(",");
            }
        }
    }

    @Override
    public void visitOrderByFields(List<Field> orderByFields)
    {
        stringBuilder.append(" ORDER BY ");
        Iterator<Field> orderByFieldsIterator = orderByFields.iterator();
        while (orderByFieldsIterator.hasNext())
        {
            stringBuilder.append(orderByFieldsIterator.next().getName());
            if (orderByFieldsIterator.hasNext())
            {
                stringBuilder.append(",");
            }
        }
    }

    @Override
    public void visitBeginExpression()
    {
        stringBuilder.append(" WHERE ");
    }

    @Override
    public void visitInitPrecedence()
    {
        stringBuilder.append("(");
    }

    @Override
    public void visitEndPrecedence()
    {
        stringBuilder.append(")");
    }

    @Override
    public void visitLimit(int limit)
    {
        stringBuilder.append(" LIMIT ").append(limit);
    }

    @Override
    public void visitOffset(int offset)
    {
        stringBuilder.append(" OFFSET ").append(offset);
    }

    @Override
    public void _dont_implement_QueryVisitor___instead_extend_DefaultQueryVisitor()
    {

    }

    @Override
    public void visitAnd()
    {
        stringBuilder.append(" AND ");
    }


    @Override
    public void visitOR()
    {
        stringBuilder.append(" OR ");
    }

    @Override
    public void visitComparison(String operator, Field field, Value value)
    {
        stringBuilder.append(field.getName()).append(operator).append(value.toString());
    }

    @Override
    public OperatorVisitor operatorVisitor()
    {
        return new DefaultOperatorVisitor();
    }

    public String dsqlQuery()
    {
        return stringBuilder.toString();
    }


}
