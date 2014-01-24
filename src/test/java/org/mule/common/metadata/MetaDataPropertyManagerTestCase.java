package org.mule.common.metadata;

import org.junit.Before;
import org.junit.Test;

import org.mule.common.metadata.MetaDataProperty;
import org.mule.common.metadata.MetaDataPropertyManager;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.metadata.field.property.dsql.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlWhereMetaDataFieldProperty;
import org.mule.common.metadata.property.exception.RepeatedPropertyException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MetaDataPropertyManagerTestCase {

    private class SomePropertyMetaDataProperty implements MetaDataProperty {
    }

    private class AnotherPropertyMetaDataProperty implements MetaDataProperty {
        private String name;
        private AnotherPropertyMetaDataProperty(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
        /**
         * Overriding equals and hashCode to be sure that adding two instantiations of this class will
         * clash within the {@link MetaDataPropertyManager#setProperties(java.util.List)}
         */
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            //Always true if its the same class
            return true;
        }

        @Override
        public int hashCode() {
            return name.hashCode();
        }
    }

    private MetaDataPropertyManager<MetaDataProperty> emptyPropertyManager;
    private MetaDataPropertyManager<MetaDataProperty> fullPropertyManager;
    private List<MetaDataProperty> properties;

    @Before
    public void setUp(){
        emptyPropertyManager = new MetaDataPropertyManager<MetaDataProperty>(new ArrayList<MetaDataProperty>());

        properties = new ArrayList<MetaDataProperty>();
        properties.add(new DsqlSelectMetaDataFieldProperty());
        properties.add(new DsqlWhereMetaDataFieldProperty());
        properties.add(new DsqlOrderMetaDataFieldProperty());
        properties.add(new DsqlQueryOperatorsMetaDataFieldProperty(
                SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(DataType.STRING)));
        fullPropertyManager = new MetaDataPropertyManager<MetaDataProperty>(properties);
    }

    @Test
    public void testHasProperty(){
        assertThat("The empty manager should not have any property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(false));
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();
        emptyPropertyManager.addProperty(aProperty);
        assertThat("The empty manager should have a property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));
        emptyPropertyManager.removeProperty(aProperty);
        assertThat("The empty manager should not have a property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(false));

        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlSelectMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlWhereMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlOrderMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should not have the property", fullPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(false));
        fullPropertyManager.addProperty(aProperty);
        assertThat("The full manager should have the property at this point", fullPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));
    }

    @Test
    public void testGetProperty(){
        assertNull("The empty manager should not have any property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataProperty.class));
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();
        emptyPropertyManager.addProperty(aProperty);
        assertThat("The empty manager should have a property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataProperty.class), is(aProperty));
        emptyPropertyManager.removeProperty(aProperty);
        assertNull("The empty manager should not have a property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataProperty.class));

        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlSelectMetaDataFieldProperty.class), is(properties.get(0)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlWhereMetaDataFieldProperty.class), is(properties.get(1)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlOrderMetaDataFieldProperty.class), is(properties.get(2)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlQueryOperatorsMetaDataFieldProperty.class), is(properties.get(3)));
        assertNull("The full manager should not have the property", fullPropertyManager.getProperty(SomePropertyMetaDataProperty.class));
        fullPropertyManager.addProperty(aProperty);
        assertThat("The full manager should have the property at this point", fullPropertyManager.getProperty(SomePropertyMetaDataProperty.class), is(aProperty));
    }

    @Test
    public void testAddingProperties(){
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();

        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        assertThat("The property should be added", emptyPropertyManager.addProperty(aProperty), is(true));
        assertThat(emptyPropertyManager.getProperties().size(), is(1));
        assertThat(emptyPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));
        assertThat(emptyPropertyManager.addProperty(aProperty), is(true));
        assertThat(emptyPropertyManager.getProperties().size(), is(1));
        assertThat(emptyPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));

        assertThat(fullPropertyManager.getProperties().size(), is(4));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(false));
        assertThat(fullPropertyManager.addProperty(aProperty), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));
        assertThat(fullPropertyManager.addProperty(aProperty), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataProperty.class), is(true));
    }

    @Test
    public void testRemoveProperties(){
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();

        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        assertThat(emptyPropertyManager.removeProperty(aProperty), is(false));
        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        assertThat(emptyPropertyManager.addProperty(aProperty), is(true));
        assertThat(emptyPropertyManager.getProperties().size(), is(1));
        assertThat(emptyPropertyManager.removeProperty(aProperty), is(true));


        assertThat(fullPropertyManager.getProperties().size(), is(4));
        assertThat(fullPropertyManager.removeProperty(aProperty), is(false));
        assertThat(fullPropertyManager.getProperties().size(), is(4));
        assertThat(fullPropertyManager.addProperty(aProperty), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat(fullPropertyManager.removeProperty(aProperty), is(true));
    }

    @Test
    public void testGetPropertiesModifications(){
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();

        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        try {
            emptyPropertyManager.getProperties().add(aProperty);
            fail("The properties list should not be modifiable");
        }catch (UnsupportedOperationException uoe){
            //Do nothing
        }

        try {
            fullPropertyManager.getProperties().add(aProperty);
            fail("The properties list should not be modifiable");
        }catch (UnsupportedOperationException uoe){
            //Do nothing
        }
    }

    @Test
    public void testInitializationRepeatedProperties(){
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();

        List<MetaDataProperty> repeatedPropertiesClass = new ArrayList<MetaDataProperty>();
        repeatedPropertiesClass.addAll(properties);
        repeatedPropertiesClass.addAll(properties);
        try{
            new MetaDataPropertyManager<MetaDataProperty>(repeatedPropertiesClass);
            fail("Creating a MetaDataPropertyManager with repeated properties must fail");
        }catch (RepeatedPropertyException repeatedPropertyEx){
            //Do nothing
        }

        List<MetaDataProperty> repeatedPropertiesClassCustom = new ArrayList<MetaDataProperty>();
        repeatedPropertiesClassCustom.add(aProperty);
        repeatedPropertiesClassCustom.add(new AnotherPropertyMetaDataProperty("a name"));
        try{
            new MetaDataPropertyManager<MetaDataProperty>(repeatedPropertiesClassCustom);
        }catch (RepeatedPropertyException repeatedPropertyEx){
            fail("Creating a MetaDataPropertyManager with unrepeated properties must not fail");
        }
        repeatedPropertiesClassCustom.add(new AnotherPropertyMetaDataProperty("a name"));
        try{
            new MetaDataPropertyManager<MetaDataProperty>(repeatedPropertiesClassCustom);
            fail("Creating a MetaDataPropertyManager with repeated properties must fail");
        }catch (RepeatedPropertyException repeatedPropertyEx){
            //Do nothing
        }
    }

    @Test
    public void testAddingRepeatedProperties(){
        MetaDataProperty aProperty =new SomePropertyMetaDataProperty();

        List<MetaDataProperty> otherCollectionProperties = new ArrayList<MetaDataProperty>();
        otherCollectionProperties.addAll(properties);

        assertThat(fullPropertyManager.getProperties().size(), is(4));
        for (MetaDataProperty metaDataProperty: otherCollectionProperties){
            assertTrue("The MetaDataPropertyManager has to have the property", fullPropertyManager.hasProperty(metaDataProperty.getClass()));
            assertThat(fullPropertyManager.addProperty(metaDataProperty), is(true));
        }

        assertThat("The size of the properties must not have changed, as the repeated property should have overridden the previous one", fullPropertyManager.getProperties().size(), is(4));
        assertThat(fullPropertyManager.addProperty(new AnotherPropertyMetaDataProperty("nameX")), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat("The property has not been added correctly",fullPropertyManager.getProperty(AnotherPropertyMetaDataProperty.class).name, is("nameX"));
        assertThat(fullPropertyManager.addProperty(new AnotherPropertyMetaDataProperty("nameY")), is(true));
        assertThat("The size of the properties must not have changed, as the repeated property should have overridden the previous one", fullPropertyManager.getProperties().size(), is(5));
        assertThat("The property has not been overridden correctly",fullPropertyManager.getProperty(AnotherPropertyMetaDataProperty.class).name, is("nameY"));
    }

}
