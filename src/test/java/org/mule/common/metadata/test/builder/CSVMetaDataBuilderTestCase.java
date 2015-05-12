package org.mule.common.metadata.test.builder;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.StructuredMetaDataModel;
import org.mule.common.metadata.builder.DefaultCSVMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;

import org.junit.Test;

public class CSVMetaDataBuilderTestCase
{

    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";

    public static final int AMOUNT_OF_FIELDS = 2;

    @Test()
    public void csvWithTwoFields()
    {
        final DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        final ListMetaDataModel model = builder.addField(FIELD_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).build();
        assertThat(model, notNullValue());
        assertThat(model.getElementModel(), instanceOf(StructuredMetaDataModel.class));
        final StructuredMetaDataModel structuredModel = (StructuredMetaDataModel) model.getElementModel();
        //Validate the object structure
        assertThat(structuredModel.getFields().size(), is(AMOUNT_OF_FIELDS));
        assertThat(structuredModel.getFields().get(0).getName(), is(FIELD_NAME));
        assertThat(structuredModel.getFields().get(0).getMetaDataModel().getDataType(), is(DataType.STRING));
        assertThat(structuredModel.getFields().get(1).getName(), is(FIELD_AGE));
        assertThat(structuredModel.getFields().get(1).getMetaDataModel().getDataType(), is(DataType.DECIMAL));
    }

    @Test(expected = IllegalArgumentException.class)
    public void onlySimpleDataTypesCanBeAddedAsFields()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        builder.addField(FIELD_NAME, DataType.MAP).build();
    }


    @Test(expected = IllegalStateException.class)
    public void atLeastOneFieldShouldBeDeclared()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        builder.build();
    }
}
