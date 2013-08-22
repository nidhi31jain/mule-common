package org.mule.common.query.dsql.parser;

import org.junit.Test;
import org.mule.common.query.DsqlQuery;
import org.mule.common.query.expression.Direction;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.FieldComparation;
import org.mule.common.query.expression.LessOperator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 *
 */
public class MuleDsqlParserTest {
    @Test
    public void testEmptyParse() {
        try {
            MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
            DsqlQuery dsqlQuery = muleDsqlParser.parse("dsql:select * from users");
            assertEquals("*", dsqlQuery.getFields().get(0).getName());
            assertEquals("users", dsqlQuery.getTypes().get(0).getName());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testWithMuleExpressionDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        DsqlQuery dsqlQuery = muleDsqlParser.parse("dsql:select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", dsqlQuery.getFields().get(0).getName());
        assertEquals(2, dsqlQuery.getTypes().size());
        assertEquals("users", dsqlQuery.getTypes().get(0).getName());
        assertEquals("addresses", dsqlQuery.getTypes().get(1).getName());
        assertTrue(dsqlQuery.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) dsqlQuery.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) dsqlQuery.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) dsqlQuery.getFilterExpression()).getValue().toString());
        assertEquals("name", dsqlQuery.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpression() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        DsqlQuery dsqlQuery = muleDsqlParser.parse("select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", dsqlQuery.getFields().get(0).getName());
        assertEquals(2, dsqlQuery.getTypes().size());
        assertEquals("users", dsqlQuery.getTypes().get(0).getName());
        assertEquals("addresses", dsqlQuery.getTypes().get(1).getName());
        assertTrue(dsqlQuery.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) dsqlQuery.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) dsqlQuery.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) dsqlQuery.getFilterExpression()).getValue().toString());
        assertEquals("name", dsqlQuery.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpressionFlowVarDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        DsqlQuery dsqlQuery = muleDsqlParser.parse("dsql:select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, dsqlQuery.getFields().size());
        assertEquals("id", dsqlQuery.getFields().get(0).getName());
        assertEquals("name", dsqlQuery.getFields().get(1).getName());
        assertEquals(2, dsqlQuery.getTypes().size());
        assertEquals("users", dsqlQuery.getTypes().get(0).getName());
        assertEquals("addresses", dsqlQuery.getTypes().get(1).getName());
        assertTrue(dsqlQuery.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) dsqlQuery.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) dsqlQuery.getFilterExpression()).getOperator() instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'", ((FieldComparation) dsqlQuery.getFilterExpression()).getValue().toString());
        assertEquals("name", dsqlQuery.getOrderByFields().get(0).getName());
        assertEquals(Direction.DESC, dsqlQuery.getDirection());
    }

    @Test
    public void testWithMuleExpressionFlowVar() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        DsqlQuery dsqlQuery = muleDsqlParser.parse("select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, dsqlQuery.getFields().size());
        assertEquals("id", dsqlQuery.getFields().get(0).getName());
        assertEquals("name", dsqlQuery.getFields().get(1).getName());
        assertEquals(2, dsqlQuery.getTypes().size());
        assertEquals("users", dsqlQuery.getTypes().get(0).getName());
        assertEquals("addresses", dsqlQuery.getTypes().get(1).getName());
        assertTrue(dsqlQuery.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) dsqlQuery.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) dsqlQuery.getFilterExpression()).getOperator()instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'",((FieldComparation) dsqlQuery.getFilterExpression()).getValue().toString());
        assertEquals("name", dsqlQuery.getOrderByFields().get(0).getName());
    }
}
