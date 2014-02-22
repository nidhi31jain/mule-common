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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.SimpleMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DefaultSimpleMetaDataModelTestCase
{
    public static final List<DataType> simpleDataTypes;

    static
    {
        simpleDataTypes = new ArrayList<DataType>(DataType.values().length);

        for (DataType dataType : DataType.values())
        {
            if (!DefaultSimpleMetaDataModel.complexTypes.contains(dataType))
            {
                simpleDataTypes.add(dataType);
            }
        }
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
        for (DataType dt : DefaultSimpleMetaDataModel.complexTypes)
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


