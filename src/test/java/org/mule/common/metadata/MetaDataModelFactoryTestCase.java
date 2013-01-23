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

import static org.junit.Assert.*;

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
    public void testGetMetaDataModelForLists()
    {
        // all list element types will appear as POJOs because there is no type information
        assertListMetaDataModel((new ArrayList<String>()).getClass());
        assertListMetaDataModel((new ArrayList<MetaDataModelFactory>()).getClass());
    }
    
    @SuppressWarnings("rawtypes")
    private void assertListMetaDataModel(Class<? extends List> clazz)
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        
        MetaDataModel model = factory.getMetaDataModel(clazz);
        assertNotNull(model);
        assertTrue(model instanceof ListMetaDataModel);
        assertSame(DataType.LIST, model.getDataType());
        ListMetaDataModel listModel = model.as(ListMetaDataModel.class);
        assertNotNull(listModel.getElementModel());
        assertEquals(new DefaultMetaDataModel(DataType.POJO), listModel.getElementModel());
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
        assertTrue(model instanceof MapMetaDataModel);
        assertSame(DataType.MAP, model.getDataType());
        
        MapMetaDataModel mapModel = model.as(MapMetaDataModel.class);
        assertNotNull(mapModel.getKeyModel());
        assertEquals(new DefaultMetaDataModel(DataType.POJO), mapModel.getKeyModel());
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
            assertEquals("DefaultMetaDataKey", pojoModel.getName());
            assertNotNull(pojoModel.getParents());
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.MetaDataKey"));
            assertNotNull(pojoModel.getFields());
            
            assertSimpleMetaDataModel(getField(pojoModel, "id"), "id", DataType.STRING);
            assertSimpleMetaDataModel(getField(pojoModel, "displayName"), "displayName", DataType.STRING);
        }

        {
            MetaDataModel model = factory.getMetaDataModel(DefaultSimpleMetaDataModel.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals("org.mule.common.metadata.DefaultSimpleMetaDataModel", pojoModel.getClassName());
            assertEquals("DefaultSimpleMetaDataModel", pojoModel.getName());
            assertNotNull(pojoModel.getParents());
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.DefaultMetaDataModel"));
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.SimpleMetaDataModel"));
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.MetaDataModel"));
            assertNotNull(pojoModel.getFields());
            
            assertSimpleMetaDataModel(getField(pojoModel, "name"), "name", DataType.STRING);
            assertSimpleListMetaDataModel(getField(pojoModel, "parentNames"), "parentNames", DataType.STRING);
        }
        {
            MetaDataModel model = factory.getMetaDataModel(DefaultSimpleMetaDataModel.class);
            assertNotNull(model);
            assertTrue(model instanceof PojoMetaDataModel);
            assertSame(DataType.POJO, model.getDataType());
            
            PojoMetaDataModel pojoModel = model.as(PojoMetaDataModel.class);
            assertEquals("org.mule.common.metadata.DefaultSimpleMetaDataModel", pojoModel.getClassName());
            assertEquals("DefaultSimpleMetaDataModel", pojoModel.getName());
            assertNotNull(pojoModel.getParents());
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.DefaultMetaDataModel"));
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.SimpleMetaDataModel"));
            assertTrue(pojoModel.getParents().contains("org.mule.common.metadata.MetaDataModel"));
            assertNotNull(pojoModel.getFields());
            
            assertSimpleMetaDataModel(getField(pojoModel, "name"), "name", DataType.STRING);
            assertSimpleListMetaDataModel(getField(pojoModel, "parentNames"), "parentNames", DataType.STRING);
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
        assertEquals("ListOfStruct", pojoModel.getName());
        assertNotNull(pojoModel.getParents());
        assertTrue(pojoModel.getParents().contains("java.lang.Object"));
        assertNotNull(pojoModel.getFields());
        assertNotNull(pojoModel.toString());

        assertSimpleListMetaDataModel(getField(pojoModel, "structList"), "structList", DataType.POJO);
        MetaDataModel struct = ((ListMetaDataModel)getField(pojoModel, "structList")).getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        assertNotNull(struct.toString());
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertNotNull(structPojo.toString());
        assertSimpleMetaDataModel(getField(structPojo, "date"), "date", DataType.DATE_TIME);
        assertSimpleMetaDataModel(getField(structPojo, "value"), "value", DataType.NUMBER);
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
            assertEquals("Node", pojoModel.getName());
            assertNotNull(pojoModel.getParents());
            assertTrue(pojoModel.getParents().contains("java.lang.Object"));
            assertNotNull(pojoModel.getFields());
            assertNotNull(pojoModel.toString());
    
            assertSimpleMetaDataModel(getField(pojoModel, "left"), "left", DataType.POJO);
            assertSimpleMetaDataModel(getField(pojoModel, "right"), "right", DataType.POJO);
            assertSimpleMetaDataModel(getField(pojoModel, "value"), "value", DataType.NUMBER);
            assertSimpleMetaDataModel(getField(pojoModel, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
    
            PojoMetaDataModel left = (PojoMetaDataModel)getField(pojoModel, "left");
            assertNotNull(left.toString());
            assertSimpleMetaDataModel(getField(left, "left"), "left", DataType.POJO);
            assertSimpleMetaDataModel(getField(left, "right"), "right", DataType.POJO);
            assertSimpleMetaDataModel(getField(left, "value"), "value", DataType.NUMBER);
            assertSimpleMetaDataModel(getField(left, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
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
        assertEquals(expectedName, pojoModel.getName());
        assertNotNull(pojoModel.getParents());
        assertTrue(pojoModel.getParents().contains("java.lang.Object"));
        assertNotNull(pojoModel.getFields());

        assertSimpleListMetaDataModel(getField(pojoModel, "structList"), "structList", DataType.POJO);
        assertSimpleMetaDataModel(getField(pojoModel, "recursiveNode"), "recursiveNode", DataType.POJO);

        ListMetaDataModel structList = (ListMetaDataModel)getField(pojoModel, "structList");
        assertNotNull(structList.toString());
        MetaDataModel struct = structList.getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertNotNull(struct.toString());
        assertSimpleMetaDataModel(getField(structPojo, "date"), "date", DataType.DATE_TIME);
        assertSimpleMetaDataModel(getField(structPojo, "value"), "value", DataType.NUMBER);
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
    
    private void assertSimpleListMetaDataModel(SimpleMetaDataModel model, String name, DataType elementDataType)
    {
        assertNotNull(model);
        assertTrue(model instanceof ListMetaDataModel);
        assertSame(DataType.LIST, model.getDataType());
        ListMetaDataModel listModel = model.as(ListMetaDataModel.class);
        assertEquals(name, model.getName());
        assertNotNull(listModel.getElementModel());
        assertEquals(new DefaultMetaDataModel(elementDataType), listModel.getElementModel());
    }
    
    private SimpleMetaDataModel getField(PojoMetaDataModel m, String name)
    {
        if (m != null)
        {
            for (SimpleMetaDataModel sm : m.getFields())
            {
                if (name.equals(sm.getName()))
                {
                    return sm;
                }
            }
        }
        return null;
    }
    
    private void assertSimpleMetaDataModel(SimpleMetaDataModel m, String name, DataType dt)
    {
        assertNotNull(m);
        assertFalse(m instanceof ListMetaDataModel);
        assertFalse(m instanceof MapMetaDataModel);
        assertEquals(name, m.getName());
        assertEquals(dt, m.getDataType());
    }
}



