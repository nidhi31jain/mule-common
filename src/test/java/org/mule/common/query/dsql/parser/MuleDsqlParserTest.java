package org.mule.common.query.dsql.parser;

import org.junit.Test;
import org.mule.common.query.QueryModel;
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
            QueryModel queryModel = muleDsqlParser.parse("dsql:select * from users");
            assertEquals("*", queryModel.getFields().get(0).getName());
            assertEquals("users", queryModel.getTypes().get(0).getName());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testWithMuleExpressionDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        QueryModel queryModel = muleDsqlParser.parse("dsql:select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", queryModel.getFields().get(0).getName());
        assertEquals(2, queryModel.getTypes().size());
        assertEquals("users", queryModel.getTypes().get(0).getName());
        assertEquals("addresses", queryModel.getTypes().get(1).getName());
        assertTrue(queryModel.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) queryModel.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) queryModel.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) queryModel.getFilterExpression()).getValue().toString());
        assertEquals("name", queryModel.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpression() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        QueryModel queryModel = muleDsqlParser.parse("select * from users, addresses where name='#[payload.get(\\'id\\')]' order by name desc");
        assertEquals("*", queryModel.getFields().get(0).getName());
        assertEquals(2, queryModel.getTypes().size());
        assertEquals("users", queryModel.getTypes().get(0).getName());
        assertEquals("addresses", queryModel.getTypes().get(1).getName());
        assertTrue(queryModel.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) queryModel.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) queryModel.getFilterExpression()).getOperator()instanceof EqualsOperator);
        assertEquals("'#[payload.get(\\'id\\')]'",((FieldComparation) queryModel.getFilterExpression()).getValue().toString());
        assertEquals("name", queryModel.getOrderByFields().get(0).getName());
    }

    @Test
    public void testWithMuleExpressionFlowVarDsql() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        QueryModel queryModel = muleDsqlParser.parse("dsql:select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, queryModel.getFields().size());
        assertEquals("id", queryModel.getFields().get(0).getName());
        assertEquals("name", queryModel.getFields().get(1).getName());
        assertEquals(2, queryModel.getTypes().size());
        assertEquals("users", queryModel.getTypes().get(0).getName());
        assertEquals("addresses", queryModel.getTypes().get(1).getName());
        assertTrue(queryModel.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) queryModel.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) queryModel.getFilterExpression()).getOperator() instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'", ((FieldComparation) queryModel.getFilterExpression()).getValue().toString());
        assertEquals("name", queryModel.getOrderByFields().get(0).getName());
        assertEquals(Direction.DESC, queryModel.getDirection());
    }

    @Test
    public void testWithMuleExpressionFlowVar() {
        MuleDsqlParser muleDsqlParser = new MuleDsqlParser();
        QueryModel queryModel = muleDsqlParser.parse("select id,name from users, addresses where name<'#[flowVars[\"id\"]]' order by name desc");
        assertEquals(2, queryModel.getFields().size());
        assertEquals("id", queryModel.getFields().get(0).getName());
        assertEquals("name", queryModel.getFields().get(1).getName());
        assertEquals(2, queryModel.getTypes().size());
        assertEquals("users", queryModel.getTypes().get(0).getName());
        assertEquals("addresses", queryModel.getTypes().get(1).getName());
        assertTrue(queryModel.getFilterExpression() instanceof FieldComparation);
        assertEquals("name", ((FieldComparation) queryModel.getFilterExpression()).getField().getName());
        assertTrue(((FieldComparation) queryModel.getFilterExpression()).getOperator()instanceof LessOperator);
        assertEquals("'#[flowVars[\"id\"]]'",((FieldComparation) queryModel.getFilterExpression()).getValue().toString());
        assertEquals("name", queryModel.getOrderByFields().get(0).getName());
    }
}
