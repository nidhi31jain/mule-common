/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.mule.common.metadata.datatype.DataType;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class MetaDataModelFactoryTestCase
{

    @Test
    public void testGetMetaDataModelForBoolean()
    {
        assertMetaDataModel(Boolean.class, DataType.BOOLEAN);
        assertMetaDataModel(boolean.class, DataType.BOOLEAN);
    }
    
    private void assertMetaDataModel(Class<?> clazz, DataType expectedDataType)
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        
        MetaDataModel model = factory.getMetaDataModel(clazz);
        assertNotNull(model);
        assertTrue(model instanceof MetaDataModel);
        assertSame(expectedDataType, model.getDataType());
    }

    @Test
    public void testGetMetaDataModelForNumbers()
    {
        assertMetaDataModel(Integer.class, DataType.NUMBER);
        assertMetaDataModel(int.class, DataType.NUMBER);
        
        assertMetaDataModel(Long.class, DataType.NUMBER);
        assertMetaDataModel(long.class, DataType.NUMBER);
        
        assertMetaDataModel(Double.class, DataType.NUMBER);
        assertMetaDataModel(double.class, DataType.NUMBER);
        
        assertMetaDataModel(Float.class, DataType.NUMBER);
        assertMetaDataModel(float.class, DataType.NUMBER);
        
        assertMetaDataModel(BigDecimal.class, DataType.NUMBER);
        
        assertMetaDataModel(AtomicInteger.class, DataType.NUMBER);
    }

    @Test
    public void testGetMetaDataModelForStrings()
    {
        assertMetaDataModel(String.class, DataType.STRING);
        assertMetaDataModel(Character.class, DataType.STRING);
        assertMetaDataModel(char.class, DataType.STRING);
    }

    @Test
    public void testGetMetaDataModelForEnums()
    {
        assertMetaDataModel(DataType.class, DataType.ENUM);
    }
    
    @Test
    public void testGetMetaDataModelForDateTimes()
    {
        assertMetaDataModel(Date.class, DataType.DATE_TIME);   
        assertMetaDataModel(GregorianCalendar.class, DataType.DATE_TIME);   
//        assertDefaultMetaDataModel(DateTime.class, DataType.DATE_TIME);   
    }
    
    @Test
    public void testGetMetaDataModelForStreams()
    {
        assertMetaDataModel(InputStream.class, DataType.STREAM);
        assertMetaDataModel(OutputStream.class, DataType.STREAM);
        assertMetaDataModel(Reader.class, DataType.STREAM);
        assertMetaDataModel(Writer.class, DataType.STREAM);
    }
    
    @Test
    public void testGetMetaDataModelForVoid()
    {
        assertMetaDataModel(Void.class, DataType.VOID);
        assertMetaDataModel(void.class, DataType.VOID);
    }
    
    @Test
    public <T> void testGetMetaDataModelForLists()
    {
        // all list element types will appear as POJOs because there is no type information
        assertListMetaDataModel((new ArrayList<String>()).getClass(), new DefaultPojoMetaDataModel(Object.class));
        assertListMetaDataModel((new ArrayList<MetaDataModelFactory>()).getClass(), new DefaultPojoMetaDataModel(Object.class));
    }
    
    @SuppressWarnings("rawtypes")
    private void assertListMetaDataModel(Class<? extends List> clazz, MetaDataModel expectedElementMetaDataModel)
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        
        MetaDataModel model = factory.getMetaDataModel(clazz);
        assertNotNull(model);
        assertTrue(model instanceof ListMetaDataModel);
        assertSame(DataType.LIST, model.getDataType());
        ListMetaDataModel listModel = model.as(ListMetaDataModel.class);
        assertNotNull(listModel.getElementModel());
        assertEquals(expectedElementMetaDataModel, listModel.getElementModel());
    }
    
    @Test
    public void testGetMetaDataModelForMaps()
    {
        // all list element types will appear as POJOs because there is no type information
        assertMapMetaDataModel((new HashMap<String, String>()).getClass());
        assertMapMetaDataModel((new TreeMap<DataType, MetaDataModelFactory>()).getClass());
    }

    @SuppressWarnings("rawtypes")
    private void assertMapMetaDataModel(Class<? extends Map> clazz)
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        
        MetaDataModel model = factory.getMetaDataModel(clazz);
        assertNotNull(model);
        assertTrue(model instanceof ParameterizedMapMetaDataModel);
        assertSame(DataType.MAP, model.getDataType());
        
        ParameterizedMapMetaDataModel mapModel = model.as(ParameterizedMapMetaDataModel.class);
        assertNotNull(mapModel.getKeyMetaDataModel());
//        assertEquals(new DefaultMetaDataModel(DataType.POJO), mapModel.getKeyMetaDataModel());
    }
    
    @Test
    public void testGetMetaDataModelForPojos()
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();

        {
            MetaDataModel model = factory.getMetaDataModel(DefaultMetaDataKey.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals("org.mule.common.metadata.DefaultMetaDataKey", pojoModel.getClassName());
            assertNotNull(pojoModel.getParentNames());
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.MetaDataKey"));
            assertNotNull(pojoModel.getFields());
            
            assertPojoMetaDataModelField(getField(pojoModel, "id"), "id", DataType.STRING);
            assertPojoMetaDataModelField(getField(pojoModel, "displayName"), "displayName", DataType.STRING);
        }

        {
            MetaDataModel model = factory.getMetaDataModel(DefaultSimpleMetaDataModel.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals("org.mule.common.metadata.DefaultSimpleMetaDataModel", pojoModel.getClassName());
            assertNotNull(pojoModel.getParentNames());
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.AbstractMetaDataModel"));
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.SimpleMetaDataModel"));
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.MetaDataModel"));
            assertNotNull(pojoModel.getFields());
        }
        {
            MetaDataModel model = factory.getMetaDataModel(DefaultPojoMetaDataModel.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals("org.mule.common.metadata.DefaultPojoMetaDataModel", pojoModel.getClassName());
            assertNotNull(pojoModel.getParentNames());
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.AbstractMetaDataModel"));
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.PojoMetaDataModel"));
            assertTrue(pojoModel.getParentNames().contains("org.mule.common.metadata.MetaDataModel"));
            assertNotNull(pojoModel.getFields());
            
            assertPojoMetaDataModelField(getField(pojoModel, "clazzName"), "clazzName", DataType.STRING);
            assertListMetaDataModelField(getField(pojoModel, "parentNames"), "parentNames", DataType.STRING);
        }
    }

    @Test
    public void testGetMetaDataModelForComplexPojos()
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();

        MetaDataModel model = factory.getMetaDataModel(ListOfStruct.class);
        assertNotNull(model);
        assertTrue(model instanceof PojoMetaDataModel);
        assertSame(DataType.POJO, model.getDataType());
        assertNotNull(model.toString());
        
        PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
        assertEquals(ListOfStruct.class.getName(), pojoModel.getClassName());
        assertNotNull(pojoModel.getParentNames());
        assertTrue(pojoModel.getParentNames().contains("java.lang.Object"));
        assertNotNull(pojoModel.getFields());
        assertNotNull(pojoModel.toString());

        assertListMetaDataModelField(getField(pojoModel, "structList"), "structList", DataType.POJO);
        MetaDataField structField = getField(pojoModel, "structList");
        MetaDataModel struct = ((ListMetaDataModel)structField.getMetaDataModel()).getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        assertNotNull(struct.toString());
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertNotNull(structPojo.toString());
        assertPojoMetaDataModelField(getField(structPojo, "date"), "date", DataType.DATE_TIME);
        assertPojoMetaDataModelField(getField(structPojo, "value"), "value", DataType.NUMBER);
    }

    @Test
    public void testGetMetaDataModelForRecursivePojos()
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        {
            MetaDataModel model = factory.getMetaDataModel(Node.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            assertNotNull(model.toString());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals(Node.class.getName(), pojoModel.getClassName());
            assertEquals("Node", pojoModel.getClassName());
            assertNotNull(pojoModel.getParentNames());
            assertTrue(pojoModel.getParentNames().contains("java.lang.Object"));
            assertNotNull(pojoModel.getFields());
            assertNotNull(pojoModel.toString());
    
            assertPojoMetaDataModelField(getField(pojoModel, "left"), "left", DataType.POJO);
            assertPojoMetaDataModelField(getField(pojoModel, "right"), "right", DataType.POJO);
            assertPojoMetaDataModelField(getField(pojoModel, "value"), "value", DataType.NUMBER);
            assertPojoMetaDataModelField(getField(pojoModel, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
    
            PojoMetaDataModel left = (PojoMetaDataModel)getField(pojoModel, "left");
            assertNotNull(left.toString());
            assertPojoMetaDataModelField(getField(left, "left"), "left", DataType.POJO);
            assertPojoMetaDataModelField(getField(left, "right"), "right", DataType.POJO);
            assertPojoMetaDataModelField(getField(left, "value"), "value", DataType.NUMBER);
            assertPojoMetaDataModelField(getField(left, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
        }
        {
            MetaDataModel model = factory.getMetaDataModel(NodeListOfStruct.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            String name = "NodeListOfStruct";
            assertNodeListOfStruct(pojoModel, name);
            PojoMetaDataModel recursiveNode = (PojoMetaDataModel)getField(pojoModel, "recursiveNode");
            name = "recursiveNode";
            assertNodeListOfStruct(recursiveNode, name);
            recursiveNode = (PojoMetaDataModel)getField(recursiveNode, "recursiveNode");
            assertNodeListOfStruct(recursiveNode, name);
            recursiveNode = (PojoMetaDataModel)getField(recursiveNode, "recursiveNode");
            assertNodeListOfStruct(recursiveNode, name);
        }
    }
    
    private void assertNodeListOfStruct(PojoMetaDataModel pojoModel, String expectedName)
    {
        assertNotNull(pojoModel.toString());
        assertEquals(NodeListOfStruct.class.getName(), pojoModel.getClassName());
        assertEquals(expectedName, pojoModel.getClassName());
        assertNotNull(pojoModel.getParentNames());
        assertTrue(pojoModel.getParentNames().contains("java.lang.Object"));
        assertNotNull(pojoModel.getFields());

        assertListMetaDataModelField(getField(pojoModel, "structList"), "structList", DataType.POJO);
        assertPojoMetaDataModelField(getField(pojoModel, "recursiveNode"), "recursiveNode", DataType.POJO);

        ListMetaDataModel structList = (ListMetaDataModel)getField(pojoModel, "structList");
        assertNotNull(structList.toString());
        MetaDataModel struct = structList.getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertNotNull(struct.toString());
        assertPojoMetaDataModelField(getField(structPojo, "date"), "date", DataType.DATE_TIME);
        assertPojoMetaDataModelField(getField(structPojo, "value"), "value", DataType.NUMBER);
    }
    
    public static class ListOfStruct
    {
        @SuppressWarnings("unused")
        private List<Struct> structList;
    }
    
    public static class Struct
    {
        Date date;
        long value = 30940L;
    }
    
    public static class Node
    {
        public Node left;
        protected Node right;
        @SuppressWarnings("unused")
        private int value;
        int defaultVisibility;
    }
    
    public static class NodeListOfStruct
    {
        @SuppressWarnings("unused")
        private List<Struct> structList;
        @SuppressWarnings("unused")
        private NodeListOfStruct recursiveNode;
    }
    
    private void assertListMetaDataModelField(MetaDataField field, String name, DataType elementDataType)
    {
        assertNotNull(field);
        assertEquals(name, field.getName());

        MetaDataModel model = field.getMetaDataModel();
        assertTrue(model instanceof ListMetaDataModel);
        assertSame(DataType.LIST, model.getDataType());
        ListMetaDataModel listModel = model.as(ListMetaDataModel.class);
        assertNotNull(listModel.getElementModel());
//        assertEquals(new DefaultMetaDataModel(elementDataType), listModel.getElementModel());
    }
    
    private MetaDataField getField(PojoMetaDataModel m, String name)
    {
        if (m != null)
        {
            for (MetaDataField mdf : m.getFields())
            {
                if (name.equals(mdf.getName()))
                {
                    return mdf;
                }
            }
        }
        return null;
    }
    
    private void assertPojoMetaDataModelField(MetaDataField field, String name, DataType dt)
    {
        assertNotNull(field);
        assertEquals(name, field.getName());
        
        MetaDataModel model = field.getMetaDataModel();
        assertFalse(model instanceof ListMetaDataModel);
        assertFalse(model instanceof ParameterizedMapMetaDataModel);
        assertEquals(dt, model.getDataType());
    }
}



