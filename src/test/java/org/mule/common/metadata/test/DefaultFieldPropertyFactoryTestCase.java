package org.mule.common.metadata.test;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.*;
import org.mule.common.metadata.field.property.dsql.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlWhereMetaDataFieldProperty;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mule.common.testutils.MuleMatchers.isExactlyA;

/**
 */
public class DefaultFieldPropertyFactoryTestCase {

    private DefaultFieldPropertyFactory defaultFieldFeatureFactory;

    @Before
    public void setUp(){
        this.defaultFieldFeatureFactory = new DefaultFieldPropertyFactory();
    }

    @Test
    public void testGetCapabilities() throws Exception{
        MetaDataModel mdm = new DefaultSimpleMetaDataModel(DataType.STRING);
        List<MetaDataFieldProperty> metaDataFieldPropertyList = defaultFieldFeatureFactory.getProperties("SomeFieldName", mdm);
        assertNotNull("the capabilities list should not be empty", metaDataFieldPropertyList);
        assertThat(metaDataFieldPropertyList.size(), is(4));
        assertThat(metaDataFieldPropertyList.get(0), isExactlyA(DsqlSelectMetaDataFieldProperty.class));
        assertThat(metaDataFieldPropertyList.get(1), isExactlyA(DsqlWhereMetaDataFieldProperty.class));
        assertThat(metaDataFieldPropertyList.get(2), isExactlyA(DsqlOrderMetaDataFieldProperty.class));
        assertThat("Operators should not be empty", ((DsqlQueryOperatorsMetaDataFieldProperty) metaDataFieldPropertyList.get(3)).getSupportedOperators().isEmpty(), is(false));


        mdm = new DefaultSimpleMetaDataModel(DataType.NUMBER);
        metaDataFieldPropertyList = defaultFieldFeatureFactory.getProperties("SomeFieldName", mdm);
        assertNotNull("the capabilities list should not be empty", metaDataFieldPropertyList);
        assertThat(metaDataFieldPropertyList.size(), is(4));
        assertThat(metaDataFieldPropertyList.get(0), isExactlyA(DsqlSelectMetaDataFieldProperty.class));
        assertThat(metaDataFieldPropertyList.get(1), isExactlyA(DsqlWhereMetaDataFieldProperty.class));
        assertThat(metaDataFieldPropertyList.get(2), isExactlyA(DsqlOrderMetaDataFieldProperty.class));
        assertThat("Operators should not be empty", ((DsqlQueryOperatorsMetaDataFieldProperty) metaDataFieldPropertyList.get(3)).getSupportedOperators().isEmpty(), is(false));
    }

}
