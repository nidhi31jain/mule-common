package org.mule.common.query.dsql.parser;

import org.junit.Test;
import org.mule.common.query.Query;
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
            Query query = muleDsqlParser.parse("dsql:select * from users");
            assertEquals("*", query.getFields().get(0).getName());
            assertEquals("users",query.getTypes().get(0).getName());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testWithMuleExpressionDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query query = muleDsqlParser.parse("dsql:select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", query.getFields().get(0).getName());
        assertEquals(2, query.getTypes().size());
        assertEquals("users",query.getTypes().get(0).getName());
        assertEquals("addresses",query.getTypes().get(1).getName());
        assertTrue(query.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) query.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) query.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) query.getFilterExpression()).getValue().toString());
        assertEquals("name", query.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpression() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query query = muleDsqlParser.parse("select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", query.getFields().get(0).getName());
        assertEquals(2, query.getTypes().size());
        assertEquals("users",query.getTypes().get(0).getName());
        assertEquals("addresses",query.getTypes().get(1).getName());
        assertTrue(query.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) query.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) query.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) query.getFilterExpression()).getValue().toString());
        assertEquals("name", query.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpressionFlowVarDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query query = muleDsqlParser.parse("dsql:select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, query.getFields().size());
        assertEquals("id",query.getFields().get(0).getName());
        assertEquals("name",query.getFields().get(1).getName());
        assertEquals(2, query.getTypes().size());
        assertEquals("users",query.getTypes().get(0).getName());
        assertEquals("addresses",query.getTypes().get(1).getName());
        assertTrue(query.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) query.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) query.getFilterExpression()).getOperator()instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'",((FieldComparation) query.getFilterExpression()).getValue().toString());
        assertEquals("name", query.getOrderByFields().get(0).getName());
        assertEquals(Direction.DESC, query.getDirection());
    }

    @Test
    public void testWithMuleExpressionFlowVar() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        Query query = muleDsqlParser.parse("select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, query.getFields().size());
        assertEquals("id",query.getFields().get(0).getName());
        assertEquals("name",query.getFields().get(1).getName());
        assertEquals(2, query.getTypes().size());
        assertEquals("users",query.getTypes().get(0).getName());
        assertEquals("addresses",query.getTypes().get(1).getName());
        assertTrue(query.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) query.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) query.getFilterExpression()).getOperator()instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'",((FieldComparation) query.getFilterExpression()).getValue().toString());
        assertEquals("name", query.getOrderByFields().get(0).getName());
    }
}
