package org.mule.common.metadata.test;

import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;
import org.mule.common.metadata.test.pojo.EverythingPojo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class DefaultMetaDataFieldTestCase {

    private class NewDummyFieldProperty implements MetaDataFieldProperty{

    }

    public static final List<DataType> simpleDataTypes = Collections.unmodifiableList(Arrays.asList(
            new DataType[]{DataType.BOOLEAN, DataType.BYTE, DataType.DATE, DataType.DATE_TIME,
                    DataType.ENUM, DataType.NUMBER, DataType.STREAM, DataType.STRING, DataType.VOID}));

    @Test
    public void testConstructorWithDefaultSimpleMetaDataModelWithoutProperties(){
        for (DataType dt : simpleDataTypes){
            DefaultSimpleMetaDataModel defaultSimpleMetaDataModel = new DefaultSimpleMetaDataModel(dt);
            testConstructorWithoutProperties(defaultSimpleMetaDataModel);
        }
    }

    @Test
    public void testConstructorWithDefaultSimpleMetaDataModelWithEmptyProperties(){
        for (DataType dt : simpleDataTypes){
            DefaultSimpleMetaDataModel defaultSimpleMetaDataModel = new DefaultSimpleMetaDataModel(dt);
            testConstructorWithEmptyProperties(defaultSimpleMetaDataModel);
        }
    }

    @Test
    public void testConstructorWithDefaultSimpleMetaDataModelWithFullProperties(){
        for (DataType dt : simpleDataTypes){
            DefaultSimpleMetaDataModel defaultSimpleMetaDataModel = new DefaultSimpleMetaDataModel(dt);
            testConstructorWithFullProperties(defaultSimpleMetaDataModel);
        }
    }

    @Test
    public void testConstructorWithDefaultPojoMetaDataModelWithoutProperties(){
        DefaultPojoMetaDataModel defaultPojoMetaDataModel = new DefaultPojoMetaDataModel(EverythingPojo.class);
        testConstructorWithoutProperties(defaultPojoMetaDataModel);
    }

        @Test
    public void testConstructorWithDefaultPojoMetaDataModelWithEmptyProperties(){
        DefaultPojoMetaDataModel defaultPojoMetaDataModel = new DefaultPojoMetaDataModel(EverythingPojo.class);
        testConstructorWithEmptyProperties(defaultPojoMetaDataModel);
    }

    @Test
    public void testConstructorDefaultPojoMetaDataModelWithFullProperties(){
        DefaultPojoMetaDataModel defaultPojoMetaDataModel = new DefaultPojoMetaDataModel(EverythingPojo.class);
        testConstructorWithFullProperties(defaultPojoMetaDataModel);
    }

    @Test
    public void testConstructorDefaultDefinedMapMetaDataModelWithoutProperties(){
        List<MetaDataField> fields = new ArrayList<MetaDataField>();
        fields.add(new DefaultMetaDataField("aString", new DefaultSimpleMetaDataModel(DataType.STRING)));
        fields.add(new DefaultMetaDataField("aBoolean", new DefaultSimpleMetaDataModel(DataType.BOOLEAN)));
        DefaultDefinedMapMetaDataModel defaultDefinedMapMetaDataModel = new DefaultDefinedMapMetaDataModel(fields, "aDynamicName");

        testConstructorWithoutProperties(defaultDefinedMapMetaDataModel);
    }

    @Test
    public void testConstructorWithDefaultDefinedMapMetaDataModelWithEmptyProperties(){
        List<MetaDataField> fields = new ArrayList<MetaDataField>();
        fields.add(new DefaultMetaDataField("aString", new DefaultSimpleMetaDataModel(DataType.STRING)));
        fields.add(new DefaultMetaDataField("aBoolean", new DefaultSimpleMetaDataModel(DataType.BOOLEAN)));
        DefaultDefinedMapMetaDataModel defaultDefinedMapMetaDataModel = new DefaultDefinedMapMetaDataModel(fields, "aDynamicName");

        testConstructorWithEmptyProperties(defaultDefinedMapMetaDataModel);
    }

    @Test
    public void testConstructorDefaultDefinedMapMetaDataModelWithFullProperties(){
        List<MetaDataField> fields = new ArrayList<MetaDataField>();
        fields.add(new DefaultMetaDataField("aString", new DefaultSimpleMetaDataModel(DataType.STRING)));
        fields.add(new DefaultMetaDataField("aBoolean", new DefaultSimpleMetaDataModel(DataType.BOOLEAN)));
        DefaultDefinedMapMetaDataModel defaultDefinedMapMetaDataModel = new DefaultDefinedMapMetaDataModel(fields, "aDynamicName");

        testConstructorWithFullProperties(defaultDefinedMapMetaDataModel);
    }

    private void testConstructorWithoutProperties(MetaDataModel metaDataModel) {
        DefaultMetaDataField defaultMetaDataField = new DefaultMetaDataField("aName", metaDataModel);
        DefaultMetaDataField defaultMetaDataFieldWithAccessType = new DefaultMetaDataField("aName", metaDataModel, MetaDataField.FieldAccessType.READ);

        assertThat("The default constructor of DefaultMetaDataField without properties as parameters, must not have empty properties",
                defaultMetaDataField.getProperties().isEmpty(), is(false));
        assertThat("The default constructor of DefaultMetaDataField with access type and without properties as parameters, must not have empty properties",
                defaultMetaDataFieldWithAccessType.getProperties().isEmpty(), is(false));
    }

    private void testConstructorWithEmptyProperties(MetaDataModel metaDataModel) {
        List<MetaDataFieldProperty> emptyFieldProperties = new ArrayList<MetaDataFieldProperty>() ;
        DefaultMetaDataField defaultMetaDataField = new DefaultMetaDataField("aName", metaDataModel, emptyFieldProperties);
        DefaultMetaDataField defaultMetaDataFieldWithAccessType = new DefaultMetaDataField("aName", metaDataModel, MetaDataField.FieldAccessType.READ, emptyFieldProperties);

        assertThat("The default constructor of DefaultMetaDataField with an empty properties list, must not return any property",
                defaultMetaDataField.getProperties().isEmpty(), is(true));
        assertThat("The default constructor of DefaultMetaDataField with access type and an empty properties list, must not return any property",
                defaultMetaDataFieldWithAccessType.getProperties().isEmpty(), is(true));
    }

    private void testConstructorWithFullProperties(MetaDataModel metaDataModel) {
        List<MetaDataFieldProperty> fullFieldProperties =  new ArrayList<MetaDataFieldProperty>();
        NewDummyFieldProperty newDummyFieldProperty = new NewDummyFieldProperty();
        fullFieldProperties.add(newDummyFieldProperty);

        DefaultMetaDataField defaultMetaDataField = new DefaultMetaDataField("aName", metaDataModel, fullFieldProperties);
        DefaultMetaDataField defaultMetaDataFieldWithAccessType = new DefaultMetaDataField("aName", metaDataModel, MetaDataField.FieldAccessType.READ, fullFieldProperties);

        assertThat("The default constructor of DefaultMetaDataField with a full properties list, must return a property",
                defaultMetaDataField.getProperties().isEmpty(), is(false));
        assertThat("The default constructor of DefaultMetaDataField with access type and a full properties list, must return a property",
                defaultMetaDataFieldWithAccessType.getProperties().isEmpty(), is(false));

        assertEquals(defaultMetaDataField.getProperty(NewDummyFieldProperty.class), newDummyFieldProperty);
        assertEquals(defaultMetaDataFieldWithAccessType.getProperty(NewDummyFieldProperty.class), newDummyFieldProperty);
    }

}
