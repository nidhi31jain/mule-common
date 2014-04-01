package org.mule.common.metadata;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.key.property.MetaDataKeyProperty;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class DefaultMetaDataKeyTestCase {

    private class SomeMetaDataKeyProperty implements MetaDataKeyProperty {
        private String value;

        private SomeMetaDataKeyProperty(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

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

    @Test
    public void testConstructorWithProperties(){
        DefaultMetaDataKey defaultMetaDataKey = new DefaultMetaDataKey("some id", "some value", fromeableMetaDataKeyProperty.getProperties());
        assertThat("Should be from capable", defaultMetaDataKey.getProperty(DsqlFromMetaDataKeyProperty.class), isA(DsqlFromMetaDataKeyProperty.class));
        defaultMetaDataKey.addProperty(new SomeMetaDataKeyProperty("a value"));
        assertThat("SomeMetaDataKeyProperty is missing", defaultMetaDataKey.getProperty(SomeMetaDataKeyProperty.class), isA(SomeMetaDataKeyProperty.class));
        assertThat("SomeMetaDataKeyProperty was not properly initialized", defaultMetaDataKey.getProperty(SomeMetaDataKeyProperty.class).getValue(), is("a value"));

        DefaultMetaDataKey anotherDefaultMetaDataKey = new DefaultMetaDataKey("some id", "some value", defaultMetaDataKey.getProperties());
        assertThat("Should be from capable", anotherDefaultMetaDataKey.getProperty(DsqlFromMetaDataKeyProperty.class), isA(DsqlFromMetaDataKeyProperty.class));
        assertThat("SomeMetaDataKeyProperty is missing", anotherDefaultMetaDataKey.getProperty(SomeMetaDataKeyProperty.class), isA(SomeMetaDataKeyProperty.class));
        assertThat("SomeMetaDataKeyProperty was not properly copied", anotherDefaultMetaDataKey.getProperty(SomeMetaDataKeyProperty.class).getValue(), is("a value"));
    }
}
