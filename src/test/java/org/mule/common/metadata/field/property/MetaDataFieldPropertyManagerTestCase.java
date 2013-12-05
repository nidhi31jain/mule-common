package org.mule.common.metadata.field.property;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.metadata.field.property.dsql.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlWhereMetaDataFieldProperty;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class MetaDataFieldPropertyManagerTestCase {

    private class SomePropertyMetaDataFieldProperty implements MetaDataFieldProperty{
    }

    private MetaDataFieldPropertyManager emptyPropertyManager;
    private MetaDataFieldPropertyManager fullPropertyManager;
    private List<MetaDataFieldProperty> properties;

    @Before
    public void setUp(){
        emptyPropertyManager = new MetaDataFieldPropertyManager(new ArrayList<MetaDataFieldProperty>());

        properties = new ArrayList<MetaDataFieldProperty>();
        properties.add(new DsqlSelectMetaDataFieldProperty());
        properties.add(new DsqlWhereMetaDataFieldProperty());
        properties.add(new DsqlOrderMetaDataFieldProperty());
        properties.add(new DsqlQueryOperatorsMetaDataFieldProperty(
                SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(DataType.STRING)));
        fullPropertyManager = new MetaDataFieldPropertyManager(properties);
    }

    @Test
    public void testHasProperty(){
        assertThat("The empty manager should not have any property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(false));
        MetaDataFieldProperty aProperty =new SomePropertyMetaDataFieldProperty();
        emptyPropertyManager.addProperty(aProperty);
        assertThat("The empty manager should have a property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));
        emptyPropertyManager.removeProperty(aProperty);
        assertThat("The empty manager should not have a property at this point", emptyPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(false));

        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlSelectMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlWhereMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlOrderMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should have the property", fullPropertyManager.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class), is(true));
        assertThat("The full manager should not have the property", fullPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(false));
        fullPropertyManager.addProperty(aProperty);
        assertThat("The full manager should have the property at this point", fullPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));
    }

    @Test
    public void testGetProperty(){
        assertNull("The empty manager should not have any property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataFieldProperty.class));
        MetaDataFieldProperty aProperty =new SomePropertyMetaDataFieldProperty();
        emptyPropertyManager.addProperty(aProperty);
        assertThat("The empty manager should have a property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataFieldProperty.class), is(aProperty));
        emptyPropertyManager.removeProperty(aProperty);
        assertNull("The empty manager should not have a property at this point", emptyPropertyManager.getProperty(SomePropertyMetaDataFieldProperty.class));

        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlSelectMetaDataFieldProperty.class), is(properties.get(0)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlWhereMetaDataFieldProperty.class), is(properties.get(1)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlOrderMetaDataFieldProperty.class), is(properties.get(2)));
        assertThat("The full manager should have the property", fullPropertyManager.getProperty(DsqlQueryOperatorsMetaDataFieldProperty.class), is(properties.get(3)));
        assertNull("The full manager should not have the property", fullPropertyManager.getProperty(SomePropertyMetaDataFieldProperty.class));
        fullPropertyManager.addProperty(aProperty);
        assertThat("The full manager should have the property at this point", fullPropertyManager.getProperty(SomePropertyMetaDataFieldProperty.class), is(aProperty));
    }

    @Test
    public void testAddingProperties(){
        MetaDataFieldProperty aProperty =new SomePropertyMetaDataFieldProperty();

        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        assertThat("The property should be added", emptyPropertyManager.addProperty(aProperty), is(true));
        assertThat(emptyPropertyManager.getProperties().size(), is(1));
        assertThat(emptyPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));
        assertThat(emptyPropertyManager.addProperty(aProperty), is(true));
        assertThat(emptyPropertyManager.getProperties().size(), is(1));
        assertThat(emptyPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));

        assertThat(fullPropertyManager.getProperties().size(), is(4));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(false));
        assertThat(fullPropertyManager.addProperty(aProperty), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));
        assertThat(fullPropertyManager.addProperty(aProperty), is(true));
        assertThat(fullPropertyManager.getProperties().size(), is(5));
        assertThat(fullPropertyManager.hasProperty(SomePropertyMetaDataFieldProperty.class), is(true));
    }

    @Test
    public void testRemoveProperties(){
        MetaDataFieldProperty aProperty =new SomePropertyMetaDataFieldProperty();

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
        MetaDataFieldProperty aProperty =new SomePropertyMetaDataFieldProperty();

        assertThat(emptyPropertyManager.getProperties().size(), is(0));
        try {
            emptyPropertyManager.getProperties().add(aProperty);
            fail("The field properties list should not be modifiable");
        }catch (UnsupportedOperationException uoe){
            //Do nothing
        }

        try {
            fullPropertyManager.getProperties().add(aProperty);
            fail("The field properties list should not be modifiable");
        }catch (UnsupportedOperationException uoe){
            //Do nothing
        }
    }


}
