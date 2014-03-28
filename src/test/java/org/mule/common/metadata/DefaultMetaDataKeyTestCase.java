package org.mule.common.metadata;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.key.property.CategoryKeyProperty;
import org.mule.common.metadata.key.property.MetaDataKeyProperty;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import java.util.List;

import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class DefaultMetaDataKeyTestCase {

    private class SomeMetaDataKeyProperty implements MetaDataKeyProperty {
        private String value;

        private SomeMetaDataKeyProperty(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SomeMetaDataKeyProperty)) return false;

            SomeMetaDataKeyProperty that = (SomeMetaDataKeyProperty) o;

            if (value != null ? !value.equals(that.value) : that.value != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return value != null ? value.hashCode() : 0;
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

    @Test
    public void testEqualsForCategories(){
        DefaultMetaDataKey bleKey = new DefaultMetaDataKey("bleh", "some label");
        DefaultMetaDataKey bleDifferentLabelKey = new DefaultMetaDataKey("bleh", "another label");
        assertTrue("Keys should be equals even if the labels are different", bleKey.equals(bleDifferentLabelKey));

        bleKey.addProperty(new SomeMetaDataKeyProperty("content 1"));
        bleDifferentLabelKey.addProperty(new SomeMetaDataKeyProperty("content 2"));
        assertTrue("Keys should be equals if the added property is not of type CategoryKeyProperty", bleKey.equals(bleDifferentLabelKey));

        String CATEGORY_ONE = "Category one";
        bleKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        bleDifferentLabelKey.addProperty(new CategoryKeyProperty("Category two"));
        assertFalse("Keys should be different if they have the same ID but different CategoryKeyProperty", bleKey.equals(bleDifferentLabelKey));

        DefaultMetaDataKey blaKey = new DefaultMetaDataKey("blah", "some label");
        assertFalse("Keys should be different if they have different ID", blaKey.equals(bleKey));

        blaKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        assertFalse("Keys should be different if they have different ID, even having the same CategoryKeyProperty", blaKey.equals(bleKey));

        DefaultMetaDataKey bleNewKey = new DefaultMetaDataKey("bleh", "some label");
        assertFalse("Keys should be different if they have the same ID but one of them is missing a CategoryKeyProperty", bleNewKey.equals(bleKey));
        assertFalse("Keys should be different if they have the same ID but one of them is missing a CategoryKeyProperty", bleKey.equals(bleNewKey));

        bleNewKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        assertTrue("Keys should be equals if they have the same ID and the same CategoryKeyProperty", bleNewKey.equals(bleKey));
        assertTrue("Keys should be equals if they have the same ID and the same CategoryKeyProperty", bleKey.equals(bleNewKey));
    }

    @Test
    public void testHashCodeForCategories(){
        DefaultMetaDataKey bleKey = new DefaultMetaDataKey("bleh", "some label");
        DefaultMetaDataKey bleDifferentLabelKey = new DefaultMetaDataKey("bleh", "another label");
        assertEquals("Keys should have the same hashcode even if the labels are different", bleKey.hashCode(), bleDifferentLabelKey.hashCode());

        bleKey.addProperty(new SomeMetaDataKeyProperty("content 1"));
        bleDifferentLabelKey.addProperty(new SomeMetaDataKeyProperty("content 2"));
        assertEquals("Keys should have the same hashcode if the added property is not of type CategoryKeyProperty", bleKey.hashCode(), bleDifferentLabelKey.hashCode());

        String CATEGORY_ONE = "Category one";
        bleKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        bleDifferentLabelKey.addProperty(new CategoryKeyProperty("Category two"));
        assertNotEquals("Keys should have different hashcode if they have the same ID but different CategoryKeyProperty", bleKey.hashCode(), bleDifferentLabelKey.hashCode());

        DefaultMetaDataKey blaKey = new DefaultMetaDataKey("blah", "some label");
        assertNotEquals("Keys should have different hashcode if they have different ID", bleKey.hashCode(), bleDifferentLabelKey.hashCode());

        blaKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        assertNotEquals("Keys should have different hashcode if they have different ID, even having the same CategoryKeyProperty", bleKey.hashCode(), bleDifferentLabelKey.hashCode());

        DefaultMetaDataKey bleNewKey = new DefaultMetaDataKey("bleh", "some label");
        assertNotEquals("Keys should have different hashcode if they have the same ID but one of them is missing a CategoryKeyProperty", bleKey.hashCode(), bleNewKey.hashCode());

        bleNewKey.addProperty(new CategoryKeyProperty(CATEGORY_ONE));
        assertEquals("Keys should have the same hashcode if they have the same ID and the same CategoryKeyProperty", bleKey.hashCode(), bleNewKey.hashCode());
        assertEquals("Keys should have the same hashcode if they have the same ID and the same CategoryKeyProperty", bleKey.hashCode(), bleNewKey.hashCode());
    }
}
