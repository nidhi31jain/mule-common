package org.mule.common.metadata.test;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.field.property.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.*;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 */
public class DefaultFieldFeatureFactoryTestCase {

    private DefaultFieldPropertyFactory defaultFieldFeatureFactory;

    @Before
    public void setUp(){
        this.defaultFieldFeatureFactory = new DefaultFieldPropertyFactory();
    }

    @Test
    public void testGetCapabilities() throws Exception{
        MetaDataModel mdm = new DefaultSimpleMetaDataModel(DataType.STRING);
        List<MetaDataFieldProperty> metaDataFieldPropertyList = defaultFieldFeatureFactory.getCapabilities("SomeFieldName", mdm);
        assertNotNull("the capabilities list should not be empty", metaDataFieldPropertyList);
        assertThat(metaDataFieldPropertyList.size(), CoreMatchers.is(5));
        assertThat((DsqlSelectMetaDataFieldProperty) metaDataFieldPropertyList.get(0), CoreMatchers.is(DsqlSelectMetaDataFieldProperty.class));
        assertThat((DsqlWhereMetaDataFieldProperty) metaDataFieldPropertyList.get(1), CoreMatchers.is(DsqlWhereMetaDataFieldProperty.class));
        assertThat((DsqlOrderMetaDataFieldProperty) metaDataFieldPropertyList.get(2), CoreMatchers.is(DsqlOrderMetaDataFieldProperty.class));
        assertThat("Operators should not be empty", ((DsqlQueryOperatorsMetaDataFieldProperty) metaDataFieldPropertyList.get(3)).getSupportedOperators().isEmpty(), CoreMatchers.is(false));
        assertThat((ImplementationClassMetaDataFieldProperty) metaDataFieldPropertyList.get(4), CoreMatchers.is(ImplementationClassMetaDataFieldProperty.class));
        assertThat(((ImplementationClassMetaDataFieldProperty) metaDataFieldPropertyList.get(4)).getName(), CoreMatchers.is("java.lang.String"));


        mdm = new DefaultSimpleMetaDataModel(DataType.NUMBER);
        metaDataFieldPropertyList = defaultFieldFeatureFactory.getCapabilities("SomeFieldName", mdm);
        assertNotNull("the capabilities list should not be empty", metaDataFieldPropertyList);
        assertThat(metaDataFieldPropertyList.size(), CoreMatchers.is(5));
        assertThat((DsqlSelectMetaDataFieldProperty) metaDataFieldPropertyList.get(0), CoreMatchers.is(DsqlSelectMetaDataFieldProperty.class));
        assertThat((DsqlWhereMetaDataFieldProperty) metaDataFieldPropertyList.get(1), CoreMatchers.is(DsqlWhereMetaDataFieldProperty.class));
        assertThat((DsqlOrderMetaDataFieldProperty) metaDataFieldPropertyList.get(2), CoreMatchers.is(DsqlOrderMetaDataFieldProperty.class));
        assertThat("Operators should not be empty", ((DsqlQueryOperatorsMetaDataFieldProperty) metaDataFieldPropertyList.get(3)).getSupportedOperators().isEmpty(), CoreMatchers.is(false));
        assertThat( metaDataFieldPropertyList.get(4), CoreMatchers.is(ImplementationClassMetaDataFieldProperty.class));
        assertThat(((ImplementationClassMetaDataFieldProperty) metaDataFieldPropertyList.get(4)).getName(), CoreMatchers.is("java.lang.Number"));
    }

}
