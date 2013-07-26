package org.mule.common.query.expression;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AbstractBinaryOperatorTest {

    @Test
    public void testOperationsSymbols(){
        assertEquals(" < ", (new LessOperator()).toString());
        assertEquals(" <= ", (new LessOrEqualsOperator()).toString());
        assertEquals(" = ", (new EqualsOperator()).toString());
        assertEquals(" > ", (new GreaterOperator()).toString());
        assertEquals(" >= ", (new GreaterOrEqualsOperator()).toString());
        assertEquals(" <> ", (new NotEqualsOperator()).toString());
        assertEquals(" like ", (new LikeOperator()).toString());
    }
}
