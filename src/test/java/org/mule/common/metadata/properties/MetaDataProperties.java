package org.mule.common.metadata.properties;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.mule.common.metadata.DefaultMetaData;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataPropertyScope;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;

/**
 *
 */
public class MetaDataProperties {

    public static final String ACCOUNT_NUMBER = "accountNumber";
    public static final String HOST = "host";
    public static final String KEEP_ALIVE = "keepAlive";
    public static final String PASSWORD = "password";

    @Test
    public void whenAddingAPropertyAndAskingForItShouldBePresent() {
        DefinedMapMetaDataModel user = new DefaultMetaDataBuilder().createDynamicObject("User").addSimpleField("name", DataType.STRING).build();
        DefaultMetaData defaultMetaData = new DefaultMetaData(user);
        defaultMetaData.addProperty(MetaDataPropertyScope.FLOW, ACCOUNT_NUMBER, new DefaultSimpleMetaDataModel(DataType.INTEGER));
        defaultMetaData.addProperty(MetaDataPropertyScope.INBOUND, HOST, new DefaultSimpleMetaDataModel(DataType.STRING));
        defaultMetaData.addProperty(MetaDataPropertyScope.OUTBOUND, KEEP_ALIVE, new DefaultSimpleMetaDataModel(DataType.BOOLEAN));
        defaultMetaData.addProperty(MetaDataPropertyScope.SESSION, PASSWORD, new DefaultSimpleMetaDataModel(DataType.STRING));

        Assert.assertThat(defaultMetaData.getProperties(MetaDataPropertyScope.FLOW).getFieldByName(ACCOUNT_NUMBER), CoreMatchers.notNullValue());
        Assert.assertThat(defaultMetaData.getProperties(MetaDataPropertyScope.INBOUND).getFieldByName(HOST), CoreMatchers.notNullValue());
        Assert.assertThat(defaultMetaData.getProperties(MetaDataPropertyScope.OUTBOUND).getFieldByName(KEEP_ALIVE), CoreMatchers.notNullValue());
        Assert.assertThat(defaultMetaData.getProperties(MetaDataPropertyScope.SESSION).getFieldByName(PASSWORD), CoreMatchers.notNullValue());

    }



}
