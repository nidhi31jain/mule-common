package org.mule.common.metadata.test;

import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.DefaultMetaDataKey;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.key.property.MetaDataKeyProperty;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

public class DefaultMetaDataKeyTestCase {
    private static final String DEFAULT_CATEGORY = "DEFAULT";

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
	public void testMetaDataKeyComparisson() {
		MetaDataKey key1 = new DefaultMetaDataKey("Aa", "Key #A");
		MetaDataKey key2 = new DefaultMetaDataKey("Ab", "Key #B");
		MetaDataKey key3 = new DefaultMetaDataKey("Ac", "Key #C");

        DefaultMetaDataKey key4 = new DefaultMetaDataKey("Aa", "Key #A");
        key4.setCategory("DEFAULT");

        /**
         * concern about {@link DefaultMetaDataKey#id} (as {@link DefaultMetaDataKey#category} has the default value)
         */
		assertTrue("A should be > B", key1.compareTo(key2) < 0);
		assertTrue("B should be > C", key2.compareTo(key3) < 0);
		assertTrue("A should be > C", key1.compareTo(key3) < 0);
		assertTrue("A should be == A", key1.compareTo(key1) == 0);
		assertTrue("B should be == C", key1.compareTo(key1) == 0);
		assertTrue("C should be == B", key1.compareTo(key1) == 0);
		assertTrue("C should be < B", key3.compareTo(key2) > 0);
		assertTrue("C should be < A", key3.compareTo(key1) > 0);
		assertTrue("B should be < A", key2.compareTo(key1) > 0);

        /**
         * concern about {@link DefaultMetaDataKey#category} and {@link DefaultMetaDataKey#id}
         */
        assertTrue("DEFAULT#A should be == DEFAULT#A", key1.compareTo(key4) == 0);
        key4.setCategory("ZDEFAULT");
        assertTrue("DEFAULT#A should be > ZDEFAULT#A", key1.compareTo(key4) < 0);
        assertTrue("ZDEFAULT#A should be < DEFAULT#A", key4.compareTo(key1) > 0);
        key4.setCategory("ADEFAULT");
        assertTrue("DEFAULT#A should be < ADEFAULT#A", key1.compareTo(key4) > 0);
        assertTrue("ADEFAULT#A should be > DEFAULT#A", key4.compareTo(key1) < 0);
	}

	@Test
	public void testMetaDataKeyTree() {
		TreeMap<MetaDataKey, String> map = new TreeMap<MetaDataKey, String>();
		MetaDataKey key1 = new DefaultMetaDataKey("Aa", "Key #A");
		MetaDataKey key2 = new DefaultMetaDataKey("Ab", "Key #B");
		MetaDataKey key3 = new DefaultMetaDataKey("Ac", "Key #C");
		map.put(key3, "Ac");
		map.put(key2, "Ab");
		map.put(key1, "Aa");
		assertTrue(map.firstKey().equals(key1));
		map.remove(key1);
		assertTrue(map.firstKey().equals(key2));
		map.remove(key2);
		assertTrue(map.firstKey().equals(key3));
	}
	
	@Test
	public void testMetadataKeyEquality() {
		MetaDataKey key1 = new DefaultMetaDataKey("Aaa", "Key #A");
		MetaDataKey key2 = new DefaultMetaDataKey("Aaa", "Key #B");
        DefaultMetaDataKey key3 = new DefaultMetaDataKey("A", "Key #A");
        key3.setCategory(DEFAULT_CATEGORY);

		assertTrue(key1.equals(key2));
		key1 = new DefaultMetaDataKey("A", "Key #A");
		key2 = new DefaultMetaDataKey("B", "Key #A");
		assertFalse(key1.equals(key2));

        assertTrue(key1.equals(key3));
        key3.setCategory("ZDEFAULT");
        assertFalse(key1.equals(key3));
        key3.setCategory("ADEFAULT");
        assertFalse(key1.equals(key3));
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
