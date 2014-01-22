package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mule.common.metadata.field.property.MetaDataFieldProperty;
import org.mule.common.metadata.field.property.exception.RepeatedFieldPropertyException;

/**
 * It ensures that for a given field, the properties of it will never collide.
 * Also, it has some helper methods to access the different properties in it
 */
public class MetaDataPropertyManager<C extends MetaDataProperty>
{

    private List<C> fieldProperties;

    public MetaDataPropertyManager(List<C> fieldProperties)
    {
        this.setFieldProperties(fieldProperties);
    }

    public MetaDataPropertyManager()
    {
        this(new ArrayList<C>());
    }

    public boolean hasProperty(Class<? extends MetaDataProperty> fieldProperty)
    {
        return this.getProperty(fieldProperty) != null;
    }

    /**
     * @param fieldPropertyClass class to look for
     * @return a MetaDataFieldProperty if the current manager has a fieldPropertyClass, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T extends MetaDataProperty> T getProperty(Class<T> fieldPropertyClass)
    {
        for (C fc : this.fieldProperties)
        {
            if (fieldPropertyClass.equals(fc.getClass()))
            {
                return (T) fc;
            }
        }
        return null;
    }

    /**
     * Adds a property, if the class of metaDataFieldProperty was already used
     * in the current manager, the manager erases the previous one
     *
     * @param fieldProperty new property to be added
     * @return true if it was added successfully, false otherwise
     */
    public boolean addProperty(C fieldProperty)
    {
        Class<C> fieldPropertyClass = (Class<C>) fieldProperty.getClass();
        if (hasProperty(fieldPropertyClass))
        {
            C alreadyDefinedProperty = getProperty(fieldPropertyClass);
            removeProperty(alreadyDefinedProperty);
        }
        return this.fieldProperties.add(fieldProperty);
    }

    /**
     * Removes a property if exists in the current manager
     *
     * @param FieldProperty
     * @return true if it was removed successfully, false otherwise
     */
    public boolean removeProperty(C FieldProperty)
    {
        if (this.fieldProperties.contains(FieldProperty))
        {
            return this.fieldProperties.remove(FieldProperty);
        }
        return false;
    }

    /**
     * @return a unmodifiable list of properties
     */
    public List<C> getProperties()
    {
        return Collections.unmodifiableList(fieldProperties);
    }

    private void setFieldProperties(List<C> fieldProperties)
    {
        if (hasRepeatedFieldProperties(fieldProperties))
        {
            throw new RepeatedFieldPropertyException("Field capabilities must no be repeated");
        }
        this.fieldProperties = fieldProperties;
    }

    private boolean hasRepeatedFieldProperties(List<C> fieldProperties)
    {
        Set<Class<?>> clazzes = new HashSet<Class<?>>();
        for (C fc : fieldProperties)
        {
            clazzes.add(fc.getClass());
        }
        return clazzes.size() != fieldProperties.size();
    }
}
