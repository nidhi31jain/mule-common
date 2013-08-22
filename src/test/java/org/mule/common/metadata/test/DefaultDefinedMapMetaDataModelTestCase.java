package org.mule.common.metadata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.mule.common.metadata.DefaultDefinedMapMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.PojoMetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.test.pojo.EverythingPojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class DefaultDefinedMapMetaDataModelTestCase
{

    private DefinedMapMetaDataModel mapMetaDataModel;

    @Before
    public void setUp()
    {
        Map<String, MetaDataModel> map = new HashMap<String, MetaDataModel>();
        map.put("field1", new DefaultSimpleMetaDataModel(DataType.STRING));
        map.put("field2", new DefaultSimpleMetaDataModel(DataType.NUMBER));

        mapMetaDataModel = new DefaultDefinedMapMetaDataModel(map, "mapName");
    }

    @Test
    public void testKeys()
    {
        assertTrue(mapMetaDataModel.getKeys().contains("field1"));
        assertTrue(mapMetaDataModel.getKeys().contains("field2"));
    }

    @Test
    public void testValues()
    {
        assertTrue(mapMetaDataModel.getValueMetaDataModel("field1").getDataType().equals(DataType.STRING));
        assertTrue(mapMetaDataModel.getValueMetaDataModel("field2").getDataType().equals(DataType.NUMBER));
    }

    @Test
    public void testFields()
    {
        assertNotNull(mapMetaDataModel.getFields());
        assertEquals(2, mapMetaDataModel.getFields().size());
        assertTrue(mapMetaDataModel.getFields().get(1).getMetaDataModel().getDataType().equals(DataType.STRING));
        assertTrue(mapMetaDataModel.getFields().get(0).getMetaDataModel().getDataType().equals(DataType.NUMBER));
    }

    @Test
    public void whenCreatingModelFromMapsWithMapsFieldsShouldBeCreatedCorrectly()
    {

        final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
        container.addSimpleField("simpleProperty", DataType.STRING)
                .addDynamicObjectField("subComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
                .addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING);

        final DefinedMapMetaDataModel metaDataModel = container.build();
        final List<MetaDataField> fields = metaDataModel.getFields();
        Assert.assertThat(fields.size(), CoreMatchers.is(2));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("subComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

        final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) metaDataModel.getValueMetaDataModel("subComplexProperty");
        Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

        final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

        Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
        Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));


    }

    @Test
    public void whenCreatingModelFromMapsWithListOfMapsFieldsShouldBeCreatedCorrectly()
    {

        final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
        container.addSimpleField("simpleProperty", DataType.STRING)
                .addListOfDynamicObjectField("subListComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
                .addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING);

        final DefinedMapMetaDataModel metaDataModel = container.build();
        final List<MetaDataField> fields = metaDataModel.getFields();
        Assert.assertThat(fields.size(), CoreMatchers.is(2));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("subListComplexProperty").getDataType(), CoreMatchers.is(DataType.LIST));

        final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) ((ListMetaDataModel) metaDataModel.getValueMetaDataModel("subListComplexProperty")).getElementModel();
        Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

        final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

        Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
        Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));

    }


    @Test
    public void whenCreatingComplexStructureFieldsShouldBeCreatedCorrectly()
    {

        final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
        container.addSimpleField("simpleProperty", DataType.STRING)
                .addListOfDynamicObjectField("subListComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
                .addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING).endDynamicObject().
                addSimpleField("subSimpleProperty2", DataType.STRING).endDynamicObject();

        final DefinedMapMetaDataModel metaDataModel = container.build();
        final List<MetaDataField> fields = metaDataModel.getFields();
        Assert.assertThat(fields.size(), CoreMatchers.is(2));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(metaDataModel.getValueMetaDataModel("subListComplexProperty").getDataType(), CoreMatchers.is(DataType.LIST));

        final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) ((ListMetaDataModel) metaDataModel.getValueMetaDataModel("subListComplexProperty")).getElementModel();
        Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(3));

        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));
        Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty2").getDataType(), CoreMatchers.is(DataType.STRING));

        final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

        Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
        Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));

    }


    @Test
    public void testListOfPojos()
    {
        ListMetaDataModel result = new DefaultMetaDataBuilder().createList().ofPojo(EverythingPojo.class).endPojo().build();
        Assert.assertThat(result.getElementModel(), CoreMatchers.is(PojoMetaDataModel.class));
        Assert.assertThat(((PojoMetaDataModel) result.getElementModel()).getFields().size(), CoreMatchers.is(17));

    }


}
