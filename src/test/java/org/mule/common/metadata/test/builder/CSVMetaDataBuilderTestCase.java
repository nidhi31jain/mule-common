package org.mule.common.metadata.test.builder;

import org.junit.Test;
import org.mule.common.metadata.CSVMetaDataModel;
import org.mule.common.metadata.builder.DefaultCSVMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CSVMetaDataBuilderTestCase
{
    public static final String FIELD_NAME = "name";
    public static final String FIELD_AGE = "age";
    public static final String FIELD_LAST_NAME = "lastname";
    public static final String COMMA_DELIMITER = ",";

    @Test()
    public void csvWithNoDelimiterShouldReturnNull()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        CSVMetaDataModel model = builder.addField(FIELD_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).build();
        assertThat(model, nullValue());
    }

    @Test()
    public void csvWithNoFieldsShouldReturnNull()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        CSVMetaDataModel model = builder.setDelimiter(COMMA_DELIMITER).build();
        assertThat(model, nullValue());
    }

    @Test()
    public void aCsvModelIsValidWhenDelimiterAndFieldsAreSet()
    {
        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        CSVMetaDataModel model = builder.setDelimiter(COMMA_DELIMITER).addField(FIELD_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).build();
        assertThat(model, notNullValue());
        assertThat(model.getDelimiter(), is(COMMA_DELIMITER));
        assertThat(model.getFields(), hasSize(2));
    }

    @Test()
    public void exampleShouldBeTheStored()
    {
        String csv = "name,lastname,age\n" +
                "alejo,abdala,25\n" +
                "pablo,cabrera,27\n";

        DefaultCSVMetaDataBuilder builder = new DefaultCSVMetaDataBuilder();
        CSVMetaDataModel model = builder.setDelimiter(COMMA_DELIMITER).setExample(csv).addField(FIELD_NAME, DataType.STRING).addField(FIELD_LAST_NAME, DataType.STRING).addField(FIELD_AGE, DataType.DECIMAL).build();
        assertThat(model, notNullValue());
        assertThat(model.getDelimiter(), is(COMMA_DELIMITER));
        assertThat(model.getFields(), hasSize(3));
        assertThat(model.getExample(), is(csv));
    }
}
