package org.mule.common.query;

import junit.framework.Assert;
import org.junit.Test;
import org.mule.common.query.expression.*;

/**
 * Test for query visitor
 */
public class DsqlQueryVisitorTest {

    @Test
    public void testBasicQueryvisitor(){
        QueryBuilder queryBuilder = new DefaultQueryBuilder();
        queryBuilder.addField(new Field("name","string"));
        queryBuilder.addField(new Field("lastName", "string"));
        queryBuilder.addType(new Type("Account"));

        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
        try {
            queryBuilder.build().accept(visitor);
        } catch (QueryBuilderException e) {

        }
        Assert.assertEquals("SELECT name,lastName FROM Account",visitor.dsqlQuery());
    }

    @Test
    public void testFiltersQueryvisitor(){
        QueryBuilder queryBuilder = new DefaultQueryBuilder();
        queryBuilder.addField(new Field("name","string"));
        queryBuilder.addType(new Type("Account"));
        Expression comparision = new FieldComparation(new LessOperator(),new Field("age","int"),new NumberValue(18));
        Expression anotherComparision = new FieldComparation(new GreaterOperator(),new Field("grade","int"), new NumberValue(0));
        Expression simpleAnd = new And(comparision,anotherComparision);
        queryBuilder.setFilterExpression(simpleAnd);

        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
        try {
            queryBuilder.build().accept(visitor);
        } catch (QueryBuilderException e) {

        }
        Assert.assertEquals("SELECT name FROM Account WHERE (age < 18 AND grade > 0)",visitor.dsqlQuery());

    }

    @Test
    public void testPrecedence() {
        QueryBuilder queryBuilder = new DefaultQueryBuilder();
        queryBuilder.addField(new Field("name","string"));
        queryBuilder.addType(new Type("Account"));
        Expression comparision = new FieldComparation(new NotEqualsOperator(),new Field("age","int"),new NumberValue(18));
        Expression anotherComparision = new FieldComparation(new GreaterOperator(),new Field("grade","int"), new NumberValue(0));
        Expression simpleOr = new Or(comparision,anotherComparision);
        Expression simpleAnd = new And(simpleOr,anotherComparision);
        queryBuilder.setFilterExpression(simpleAnd);

        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
        try {
            queryBuilder.build().accept(visitor);
        } catch (QueryBuilderException e) {

        }
        System.out.println(visitor.dsqlQuery());
        Assert.assertEquals("SELECT name FROM Account WHERE ((age <> 18 OR grade > 0) AND grade > 0)",visitor.dsqlQuery());
    }

    @Test
    public void testOrderBy() {
        QueryBuilder queryBuilder = new DefaultQueryBuilder();
        queryBuilder.addField(new Field("name","string"));
        queryBuilder.addType(new Type("Account"));
        queryBuilder.addOrderByField(new Field("name","string"));
        queryBuilder.addOrderByField(new Field("age","int"));
        Expression comparision = new FieldComparation(new LessOperator(),new Field("age","int"),new NumberValue(18));
        Expression anotherComparision = new FieldComparation(new EqualsOperator(),new Field("grade","int"), new NumberValue(0));
        Expression simpleOr = new Or(comparision,anotherComparision);
        Expression simpleAnd = new And(simpleOr,anotherComparision);
        queryBuilder.setFilterExpression(simpleAnd);

        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
        try {
            queryBuilder.build().accept(visitor);
        } catch (QueryBuilderException e) {

        }
        Assert.assertEquals("SELECT name FROM Account WHERE ((age < 18 OR grade = 0) AND grade = 0) ORDER BY name,age", visitor.dsqlQuery());
    }

    @Test
    public void testLimitAndOffset() {
        QueryBuilder queryBuilder = new DefaultQueryBuilder();
        queryBuilder.addField(new Field("name","string"));
        queryBuilder.addType(new Type("Account"));
        queryBuilder.addOrderByField(new Field("name","string"));
        queryBuilder.addOrderByField(new Field("age","int"));
        queryBuilder.setLimit(10);
        queryBuilder.setOffset(20);
        Expression comparision = new FieldComparation(new LessOperator(),new Field("age","int"),new NumberValue(18));
        Expression anotherComparision = new FieldComparation(new GreaterOperator(),new Field("grade","int"), new NumberValue(0));
        Expression simpleOr = new Or(comparision,anotherComparision);
        Expression simpleAnd = new And(simpleOr,anotherComparision);
        queryBuilder.setFilterExpression(simpleAnd);

        DsqlQueryVisitor visitor = new DsqlQueryVisitor();
        try {
            queryBuilder.build().accept(visitor);
        } catch (QueryBuilderException e) {

        }
        Assert.assertEquals("SELECT name FROM Account WHERE ((age < 18 OR grade > 0) AND grade > 0) ORDER BY name,age LIMIT 10 OFFSET 20",visitor.dsqlQuery());
    }
}
