/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.metadata.test;


import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.FieldMetaDataModel;
import org.mule.common.metadata.SimpleMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.LikeOperator;
import org.mule.common.query.expression.Operator;

import static org.junit.Assert.*;

public class DefaultSimpleMetaDataModelTestCase
{
    public static final List<DataType> simpleDataTypes = Collections.unmodifiableList(Arrays.asList(
        new DataType[] { DataType.BOOLEAN, DataType.BYTE, DataType.DATE_TIME, 
        DataType.ENUM, DataType.NUMBER, DataType.STREAM, DataType.STRING, DataType.VOID}));
    
    public static final List<DataType> complexDataTypes;
    static
    {
        List<DataType> complexTypes = new ArrayList<DataType>(Arrays.asList(DataType.values()));
        complexTypes.removeAll(simpleDataTypes);
        complexDataTypes = Collections.unmodifiableList(complexTypes); 
    }

    @Test
    public void testFieldCapabilityWithDataType()
    {
        FieldMetaDataModel smdm = new DefaultSimpleMetaDataModel(DataType.BOOLEAN);
        assertTrue(smdm.isSelectCapable());
        assertTrue(smdm.isWhereCapable());
        assertTrue(smdm.isSortCapable());
        assertFalse(smdm.getSupportedOperators().isEmpty());

        smdm = new DefaultSimpleMetaDataModel(DataType.VOID);
        assertTrue(smdm.isSelectCapable());
        assertFalse(smdm.isWhereCapable());
        assertTrue(smdm.isSortCapable());
        assertTrue(smdm.getSupportedOperators().isEmpty());
    }

    @Test
    public void testFieldCapabilityWithDataTypeBoolAndOperators()
    {
        FieldMetaDataModel smdm = new DefaultSimpleMetaDataModel(DataType.BOOLEAN, false, false, false);
        assertFalse(smdm.isSelectCapable());
        assertFalse(smdm.isSortCapable());
        assertFalse(smdm.isWhereCapable());
        assertTrue(smdm.getSupportedOperators().isEmpty());

        FieldMetaDataModel smdmWhere = new DefaultSimpleMetaDataModel(DataType.BOOLEAN, false, false, true);
        assertFalse(smdmWhere.isSelectCapable());
        assertTrue(smdmWhere.isWhereCapable());
        assertFalse(smdmWhere.isSortCapable());
        assertFalse(smdmWhere.getSupportedOperators().isEmpty());

        smdm = new DefaultSimpleMetaDataModel(DataType.BOOLEAN, new ArrayList<Operator>());
        assertTrue(smdm.isSelectCapable());
        assertFalse(smdm.isWhereCapable());
        assertTrue(smdm.isSortCapable());
        assertTrue(smdm.getSupportedOperators().isEmpty());

        smdm = new DefaultSimpleMetaDataModel(DataType.STRING, false, false, new ArrayList<Operator>());
        assertFalse(smdm.isSelectCapable());
        assertFalse(smdm.isWhereCapable());
        assertFalse(smdm.isSortCapable());
        assertTrue(smdm.getSupportedOperators().isEmpty());

        smdm = new DefaultSimpleMetaDataModel(DataType.STRING, Arrays.<Operator>asList(new EqualsOperator(), new LikeOperator()));
        assertTrue(smdm.isSelectCapable());
        assertTrue(smdm.isWhereCapable());
        assertTrue(smdm.isSortCapable());
        assertFalse(smdm.getSupportedOperators().isEmpty());

        smdm = new DefaultSimpleMetaDataModel(DataType.STRING, false, false, new ArrayList<Operator>());
        assertFalse(smdm.isSelectCapable());
        assertFalse(smdm.isWhereCapable());
        assertFalse(smdm.isSortCapable());
        assertTrue(smdm.getSupportedOperators().isEmpty());

    }

    @Test
    public void testBasicDataTypes()
    {
        for (DataType dt : simpleDataTypes)
        {
            SimpleMetaDataModel smdm = new DefaultSimpleMetaDataModel(dt);
            assertEquals(dt, smdm.getDataType());
            assertSame(dt, smdm.getDataType());
        }
    }
    
    @Test
    public void testComplexDataTypes()
    {
        for (DataType dt : complexDataTypes)
        {
            try
            {
                new DefaultSimpleMetaDataModel(dt);
                fail("Should not be able to create a SimpleMetaDataModel with a complex type: " + dt.name());
            }
            catch (IllegalArgumentException e)
            {
                // expected
            }
        }
    }
}


