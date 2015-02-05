package org.mule.common.metadata.test.json.sample;


import org.junit.Assert;
import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;

import java.io.InputStream;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;


public class JSONSampleMetaDataModelTest {

    JSONSampleMetadataModelFactory modelFactory = new JSONSampleMetadataModelFactory();

    @Test
    public void testEmptyObject() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/emptyObject.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(0));
    }

    @Test
    public void testArray() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/emptyArray.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        // If the Array is empty we assume the type is String
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.STRING));
    }

    @Test
    public void testSimpleObject() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/simpleObject.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(1));
    }

    @Test
    public void testSimpleArray() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/simpleArray.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.INTEGER));
    }

    @Test
    public void testMultiplesKeyValuePairObject() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/multiplesKeyValuePairObject.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(2));
    }

    @Test
    public void testObjectNestedWithObject() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/objectNestedWithObject.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(1));

        // Checking nested object
        DefaultMetaDataField root = (DefaultMetaDataField) ((DefaultStructuredMetadataModel)metaDataModel).getFields().get(0);

        Assert.assertThat(root.getName(), is("Root"));
        Assert.assertThat(((DefaultStructuredMetadataModel)root.getMetaDataModel()).getFields().size(), is(2));
    }

    @Test
    public void testObjectNestedWithObjectTwoLevels() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/objectNestedWithObjectTwoLevels.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(1));

        // Checking nested object
        DefaultMetaDataField root = (DefaultMetaDataField) ((DefaultStructuredMetadataModel)metaDataModel).getFields().get(0);

        Assert.assertThat(root.getName(), is("Root"));
        Assert.assertThat(((DefaultStructuredMetadataModel)root.getMetaDataModel()).getFields().size(), is(3));

        // Checking second nested object
        DefaultMetaDataField secondNestedObject = (DefaultMetaDataField) ((DefaultStructuredMetadataModel)root.getMetaDataModel()).getFields().get(2);

        Assert.assertThat(secondNestedObject.getName(), is("Key3"));
        Assert.assertThat(((DefaultStructuredMetadataModel)secondNestedObject.getMetaDataModel()).getFields().size(), is(3));
    }

    @Test
    public void testArrayWithObjects() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/arrayWithObjects.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.JSON));

        // Getting first object from array
        MetaDataModel elementModel = ((DefaultListMetaDataModel) metaDataModel).getElementModel();

        Assert.assertThat(elementModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(elementModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)elementModel).getFields().size(), is(2));
    }

    @Test
    public void arrayWithNestedObjects() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/arrayWithNestedObjects.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.JSON));

        // Getting first object from array
        MetaDataModel elementModel = ((DefaultListMetaDataModel) metaDataModel).getElementModel();

        Assert.assertThat(elementModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(elementModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)elementModel).getFields().size(), is(1));

        // Checking nested object
        MetaDataField root = ((DefaultStructuredMetadataModel) elementModel).getFields().get(0);

        Assert.assertThat(root.getName(), is("Root"));
        Assert.assertThat(((DefaultStructuredMetadataModel)root.getMetaDataModel()).getFields().size(), is(2));
    }

    @Test
    public void testArrayNestedWithArray() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/arrayNestedWithArray.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.LIST));
    }

    @Test
    public void testArrayNestedWithArrayAndObject() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/arrayNestedWithArrayAndObject.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);

        Assert.assertThat(metaDataModel.getDataType(), is(DataType.LIST));
        Assert.assertThat(metaDataModel, instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)metaDataModel).getElementModel().getDataType(), is(DataType.LIST));

        // Checking nested list
        MetaDataModel nestedList = ((DefaultListMetaDataModel) ((DefaultListMetaDataModel) metaDataModel).getElementModel()).getElementModel();

        Assert.assertThat(nestedList.getDataType(), is(DataType.JSON));
        Assert.assertThat(nestedList, instanceOf(DefaultStructuredMetadataModel.class));

        MetaDataField root = ((DefaultStructuredMetadataModel) nestedList).getFields().get(0);

        Assert.assertThat(root.getName(), is("Root"));
        Assert.assertThat(((DefaultStructuredMetadataModel)root.getMetaDataModel()).getFields().size(), is(2));
    }

    @Test
    public void testObjectWithArray() throws Exception {
        InputStream jsonSample = getClass().getClassLoader().getResourceAsStream("jsonSample/objectWithArray.json");
        String json = new Scanner(jsonSample).useDelimiter("\\A").next();

        MetaDataModel metaDataModel = modelFactory.buildModel(json);
        
        Assert.assertThat(metaDataModel.getDataType(), is(DataType.JSON));
        Assert.assertThat(metaDataModel, instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel)metaDataModel).getFields().size(), is(2));

        // Checking nested list
        MetaDataModel nestedList = ((DefaultMetaDataField) ((DefaultStructuredMetadataModel) metaDataModel).getFields().get(0)).getMetaDataModel();

        Assert.assertThat(nestedList.getDataType(), is(DataType.LIST));
        Assert.assertThat(nestedList, instanceOf(DefaultListMetaDataModel.class));

        // Checking nested element
        MetaDataModel elementModel = ((DefaultListMetaDataModel) nestedList).getElementModel();

        Assert.assertThat(elementModel.getDataType(), is(DataType.INTEGER));
        Assert.assertThat(elementModel, instanceOf(DefaultSimpleMetaDataModel.class));
    }
}