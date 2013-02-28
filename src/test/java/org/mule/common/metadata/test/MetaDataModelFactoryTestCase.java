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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.MetaDataModelFactory;
import org.mule.common.metadata.ParameterizedMapMetaDataModel;
import org.mule.common.metadata.PojoMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public class MetaDataModelFactoryTestCase
{

    @Test
    public void testGetMetaDataModelForListPojoFields()
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();

        List<MetaDataField> fieldsForStruct = factory.getFieldsForClass(ListOfStruct.class);
        assertNotNull(fieldsForStruct);

        MetaDataField structField = getField(fieldsForStruct, "structList");
		assertListMetaDataModelField(structField, "structList", DataType.POJO);
        MetaDataModel struct = ((ListMetaDataModel)structField.getMetaDataModel()).getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        assertNotNull(struct.toString());
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertTrue(structPojo.getClassName().equals(Struct.class.getName()));
        assertNotNull(structPojo.toString());
        assertPojoMetaDataModelField(getField(structPojo.getFields(), "date"), "date", DataType.DATE_TIME);
        assertPojoMetaDataModelField(getField(structPojo.getFields(), "value"), "value", DataType.NUMBER);
    }

    @Test
    public void testGetMetaDataModelForMapFields()
    {
    	MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
    	
    	List<MetaDataField> fieldsForMapStruct = factory.getFieldsForClass(MapOfStruct.class);
    	assertNotNull(fieldsForMapStruct);
    	
    	MetaDataField structField = getField(fieldsForMapStruct, "structMap");
    	assertParamMapMetaDataModelField(structField, "structMap", DataType.POJO, DataType.POJO);
    	
    	MetaDataModel keyStruct = ((ParameterizedMapMetaDataModel)structField.getMetaDataModel()).getKeyMetaDataModel();
    	MetaDataModel valueStruct = ((ParameterizedMapMetaDataModel)structField.getMetaDataModel()).getValueMetaDataModel();
    	
    	assertTrue(keyStruct instanceof PojoMetaDataModel);
    	assertTrue(valueStruct instanceof PojoMetaDataModel);
    	
    	PojoMetaDataModel keyStructPojo = (PojoMetaDataModel)keyStruct;
    	PojoMetaDataModel valueStructPojo = (PojoMetaDataModel)valueStruct;
    	
    	assertTrue(keyStructPojo.getClassName().equals(Struct.class.getName()));
    	assertTrue(valueStructPojo.getClassName().equals(Struct1.class.getName()));
    	
    	assertPojoMetaDataModelField(getField(keyStructPojo.getFields(), "date"), "date", DataType.DATE_TIME);
    	assertPojoMetaDataModelField(getField(keyStructPojo.getFields(), "value"), "value", DataType.NUMBER);
    	
    	assertPojoMetaDataModelField(getField(valueStructPojo.getFields(), "date"), "date", DataType.DATE_TIME);
    	assertPojoMetaDataModelField(getField(valueStructPojo.getFields(), "name"), "name", DataType.STRING);
    	assertPojoMetaDataModelField(getField(valueStructPojo.getFields(), "value"), "value", DataType.NUMBER);
    }

    @Test
    public void testGetMetaDataModelForRecursivePojos()
    {
        MetaDataModelFactory factory = MetaDataModelFactory.getInstance();
        {
            List<MetaDataField> fieldsForNode = factory.getFieldsForClass(Node.class);
            assertNotNull(fieldsForNode);

            MetaDataField leftField = getField(fieldsForNode, "left");
			assertPojoMetaDataModelField(leftField, "left", DataType.POJO);
            assertPojoMetaDataModelField(getField(fieldsForNode, "right"), "right", DataType.POJO);
            assertPojoMetaDataModelField(getField(fieldsForNode, "value"), "value", DataType.NUMBER);
            assertPojoMetaDataModelField(getField(fieldsForNode, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
            
            MetaDataModel leftMetaDataModel = leftField.getMetaDataModel();
            assertTrue(leftMetaDataModel instanceof PojoMetaDataModel);
            PojoMetaDataModel leftPojoMetaDataModel = leftMetaDataModel.as(PojoMetaDataModel.class);
            assertTrue(leftPojoMetaDataModel.getClassName().equals(Node.class.getName()));
            List<MetaDataField> leftNodeFields = leftPojoMetaDataModel.getFields();
            
            assertPojoMetaDataModelField(getField(leftNodeFields, "left"), "left", DataType.POJO);
            assertPojoMetaDataModelField(getField(leftNodeFields, "right"), "right", DataType.POJO);
            assertPojoMetaDataModelField(getField(leftNodeFields, "value"), "value", DataType.NUMBER);
            assertPojoMetaDataModelField(getField(leftNodeFields, "defaultVisibility"), "defaultVisibility", DataType.NUMBER);
        }
        {
            List<MetaDataField> fieldsForNodeListOfStruct = factory.getFieldsForClass(NodeListOfStruct.class);
            assertNotNull(fieldsForNodeListOfStruct);
            
            MetaDataField structListField = getField(fieldsForNodeListOfStruct,  "structList");
            MetaDataField recursiveListField = getField(fieldsForNodeListOfStruct,  "recursiveNode");
            assertPojoListMetaDataModelField(structListField, "structList", Struct.class);
            
            String name = NodeListOfStruct.class.getName();
            PojoMetaDataModel recursiveNode = (PojoMetaDataModel)recursiveListField.getMetaDataModel();
            assertNodeListOfStruct(recursiveNode, name);
            recursiveNode = (PojoMetaDataModel)getField(recursiveNode.getFields(), "recursiveNode").getMetaDataModel();
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

        assertListMetaDataModelField(getField(pojoModel.getFields(), "structList"), "structList", DataType.POJO);
        assertPojoMetaDataModelField(getField(pojoModel.getFields(), "recursiveNode"), "recursiveNode", DataType.POJO);

        ListMetaDataModel structList = (ListMetaDataModel)getField(pojoModel.getFields(), "structList").getMetaDataModel();
        assertNotNull(structList.toString());
        MetaDataModel struct = structList.getElementModel();
        assertTrue(struct instanceof PojoMetaDataModel);
        PojoMetaDataModel structPojo = (PojoMetaDataModel)struct;
        assertNotNull(struct.toString());
        assertPojoMetaDataModelField(getField(structPojo.getFields(), "date"), "date", DataType.DATE_TIME);
        assertPojoMetaDataModelField(getField(structPojo.getFields(), "value"), "value", DataType.NUMBER);
    }
    
    public static class ListOfStruct
    {
        @SuppressWarnings("unused")
        private List<Struct> structList;

		public List<Struct> getStructList() {
			return structList;
		}

		public void setStructList(List<Struct> structList) {
			this.structList = structList;
		}
    }

    public static class MapOfStruct
    {
    	@SuppressWarnings("unused")
    	private Map<Struct, Struct1> structMap;

		public Map<Struct, Struct1> getStructMap() {
			return structMap;
		}

		public void setStructMap(Map<Struct, Struct1> structMap) {
			this.structMap = structMap;
		}
    }
    
    public static class Struct
    {
        Date date;
        long value = 30940L;
		
        public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public long getValue() {
			return value;
		}
		public void setValue(long value) {
			this.value = value;
		}
    }
    
    public static class Struct1
    {
    	Date date;
    	String name;
    	long value = 30941L;
		public Date getDate() {
			return date;
		}
		public void setDate(Date date) {
			this.date = date;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public long getValue() {
			return value;
		}
		public void setValue(long value) {
			this.value = value;
		}
    }
    
    public static class Node
    {
        public Node left;
        protected Node right;
        @SuppressWarnings("unused")
        private int value;
        int defaultVisibility;
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
		public int getDefaultVisibility() {
			return defaultVisibility;
		}
		public void setDefaultVisibility(int defaultVisibility) {
			this.defaultVisibility = defaultVisibility;
		}
    }
    
    public static class NodeListOfStruct
    {
        @SuppressWarnings("unused")
        private List<Struct> structList;
        @SuppressWarnings("unused")
        private NodeListOfStruct recursiveNode;
		public List<Struct> getStructList() {
			return structList;
		}
		public void setStructList(List<Struct> structList) {
			this.structList = structList;
		}
		public NodeListOfStruct getRecursiveNode() {
			return recursiveNode;
		}
		public void setRecursiveNode(NodeListOfStruct recursiveNode) {
			this.recursiveNode = recursiveNode;
		}
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
    }

    private void assertPojoListMetaDataModelField(MetaDataField field, String name, Class<?> clazz)
    {
    	assertNotNull(field);
    	assertEquals(name, field.getName());
    	
    	MetaDataModel model = field.getMetaDataModel();
    	assertTrue(model instanceof ListMetaDataModel);
    	assertSame(DataType.LIST, model.getDataType());
    	ListMetaDataModel listModel = model.as(ListMetaDataModel.class);
    	assertNotNull(listModel.getElementModel());
    	assertNotNull(listModel.getElementModel().getDataType().equals(DataType.POJO));
    	PojoMetaDataModel listPojoModel = listModel.getElementModel().as(PojoMetaDataModel.class);
    	assertEquals(listPojoModel.getClassName(), clazz.getName());
    }
    
    private void assertParamMapMetaDataModelField(MetaDataField field, String name, DataType keyDataType, DataType valueDataType)
    {
    	assertNotNull(field);
    	assertEquals(name, field.getName());
    	
    	MetaDataModel model = field.getMetaDataModel();
    	
    	assertTrue(model instanceof ParameterizedMapMetaDataModel);
    	assertSame(DataType.MAP, model.getDataType());
    	
    	ParameterizedMapMetaDataModel mapModel = model.as(ParameterizedMapMetaDataModel.class);
    	assertNotNull(mapModel.getKeyMetaDataModel());
    	assertNotNull(mapModel.getValueMetaDataModel());
    	
    	assertEquals(keyDataType, mapModel.getKeyMetaDataModel().getDataType());
    	assertEquals(valueDataType, mapModel.getValueMetaDataModel().getDataType());
    }
    
    private MetaDataField getField(List<MetaDataField> fields, String name)
    {
        if (fields != null)
        {
            for (MetaDataField mdf : fields)
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



