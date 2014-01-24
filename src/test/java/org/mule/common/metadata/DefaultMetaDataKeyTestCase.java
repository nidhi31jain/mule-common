package org.mule.common.metadata;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class DefaultMetaDataKeyTestCase {

    DefaultMetaDataKey implicitFromeableMetaDataKey;
    DefaultMetaDataKey notFromeableMetaDataKey;
    DefaultMetaDataKey fromeableMetaDataKeyProperty;

    @Before
    public void setUp(){
        implicitFromeableMetaDataKey = new DefaultMetaDataKey("id-implicitFromeable", "displayName-implicitFromeable");
        notFromeableMetaDataKey = new DefaultMetaDataKey("id-notFromeable", "displayName-notFromeable", false);
        fromeableMetaDataKeyProperty = new DefaultMetaDataKey("id-fromeableProperty", "displayName-fromeableProperty");
        fromeableMetaDataKeyProperty.addProperty(new DsqlFromMetaDataKeyProperty());
    }

    @Test
    public void testFromCapable(){
        assertThat("Should be from capable", implicitFromeableMetaDataKey.isFromCapable(), is(true));
        assertThat("Should be from capable", implicitFromeableMetaDataKey.getProperty(DsqlFromMetaDataKeyProperty.class), isA(DsqlFromMetaDataKeyProperty.class));

        assertThat("Should not be from capable", notFromeableMetaDataKey.isFromCapable(), is(false));
        assertThat("Should not be from capable", notFromeableMetaDataKey.getProperty(DsqlFromMetaDataKeyProperty.class), is(nullValue()));

        assertThat("Should be from capable", fromeableMetaDataKeyProperty.isFromCapable(), is(true));
        assertThat("Should be from capable", fromeableMetaDataKeyProperty.getProperty(DsqlFromMetaDataKeyProperty.class), isA(DsqlFromMetaDataKeyProperty.class));
    }
}
