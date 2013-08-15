package org.mule.common.metadata.test;

import org.junit.Test;
import org.mule.common.metadata.DefaultQueryCapability;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.NotEqualsOperator;
import org.mule.common.query.expression.Operator;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 */
public class DefaultQueryCapabilityTestCase {

    @Test
    public void testConstructors()
    {
        DefaultQueryCapability dfq = new DefaultQueryCapability();
        assertTrue(dfq.isSelectCapable());
        assertTrue(dfq.isWhereCapable());
        assertTrue(dfq.isSortCapable());
        assertTrue(dfq.getSupportedOperators().isEmpty());

        dfq = new DefaultQueryCapability(true, true, false);
        assertTrue(dfq.isSelectCapable());
        assertFalse(dfq.isWhereCapable());
        assertTrue(dfq.isSortCapable());
        assertTrue(dfq.getSupportedOperators().isEmpty());

        dfq = new  DefaultQueryCapability(true, true, new ArrayList<Operator>());
        assertTrue(dfq.isSelectCapable());
        assertFalse(dfq.isWhereCapable());
        assertTrue(dfq.isSortCapable());
        assertTrue(dfq.getSupportedOperators().isEmpty());

        dfq = new  DefaultQueryCapability(true, true, Arrays.<Operator>asList(new EqualsOperator(), new NotEqualsOperator()));
        assertTrue(dfq.isSelectCapable());
        assertTrue(dfq.isWhereCapable());
        assertTrue(dfq.isSortCapable());
        assertFalse(dfq.getSupportedOperators().isEmpty());
    }

    @Test
    public void testGenerateSupportedOperators()
    {
        DefaultQueryCapability dfq = new DefaultQueryCapability(false, false, false);
        assertFalse(dfq.isSelectCapable());
        assertFalse(dfq.isWhereCapable());
        assertFalse(dfq.isSortCapable());
        assertTrue(dfq.getSupportedOperators().isEmpty());

        dfq.generateSupportedOperators(DataType.BOOLEAN);
        assertTrue(dfq.isWhereCapable());
        assertFalse(dfq.getSupportedOperators().isEmpty());
    }
}
