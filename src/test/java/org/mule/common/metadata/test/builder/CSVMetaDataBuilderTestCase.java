package org.mule.common.metadata.test.builder;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.StructuredMetaDataModel;
import org.mule.common.metadata.builder.DefaultCSVMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.CSVHasHeadersMetaDataProperty;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import org.junit.Test;

public class CSVMetaDataBuilderTestCase
{

    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_LAST_NAME = "lastname";

    public static final String EXAMPLE = "name,lastname,age\n" +
                                         "alejo,abdala,25\n" +
                                         "pablo,cabrera,27\n";

    @Test(expected = IllegalStateException.class)
    public void csvWithNoFieldsShouldFail()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        ListMetaDataModel build = builder.setExample(EXAMPLE).setHasHeaders(true).build();
        fail();
    }

    @Test()
    public void csvBuilderTest()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        ListMetaDataModel model = builder.addField(FIELD_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).build();
        StructuredMetaDataModel csv = (StructuredMetaDataModel) model.getElementModel();
        assertThat(csv.getDataType(), is(DataType.CSV));
        assertThat(csv.getFields(), hasSize(2));
    }

    @Test()
    public void hasHeaderFieldShouldBeStored()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        ListMetaDataModel model = builder.addField(FIELD_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).setHasHeaders(false).build();
        StructuredMetaDataModel csv = (StructuredMetaDataModel) model.getElementModel();
        assertThat(csv.getDataType(), is(DataType.CSV));
        assertThat(csv.getFields(), hasSize(2));

        CSVHasHeadersMetaDataProperty hasHeadersMetaDataProperty = csv.getProperty(CSVHasHeadersMetaDataProperty.class);
        assertThat(hasHeadersMetaDataProperty, notNullValue());
        assertFalse(hasHeadersMetaDataProperty.hasHeaders());
    }

    @Test()
    public void exampleFieldShouldBeStored()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        ListMetaDataModel model = builder.addField(FIELD_NAME, DataType.STRING).addField(FIELD_LAST_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).setExample(EXAMPLE).build();
        StructuredMetaDataModel csv = (StructuredMetaDataModel) model.getElementModel();
        assertThat(csv.getDataType(), is(DataType.CSV));
        assertThat(csv.getFields(), hasSize(3));

        TextBasedExampleMetaDataModelProperty exampleMetaDataProperty = csv.getProperty(TextBasedExampleMetaDataModelProperty.class);
        assertThat(exampleMetaDataProperty, notNullValue());
        assertThat(exampleMetaDataProperty.getExampleContent(), is(EXAMPLE));
    }
}
