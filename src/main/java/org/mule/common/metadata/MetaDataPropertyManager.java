package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mule.common.metadata.property.exception.RepeatedPropertyException;

/**
 * Contains a group of properties, while ensures that any of those properties will ever collide.
 * Also, it has some helper methods to access the different properties in it
 */
public class MetaDataPropertyManager<C extends MetaDataProperty>
{

    private List<C> properties;

    public MetaDataPropertyManager(List<C> properties)
    {
        this.setProperties(properties);
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
     * @param propertyClass class to look for
     * @return a MetaDataFieldProperty if the current manager has a propertyClass, null otherwise
     */
    @SuppressWarnings("unchecked")
    public <T extends MetaDataProperty> T getProperty(Class<T> propertyClass)
    {
        for (C fc : this.properties)
        {
            if (propertyClass.equals(fc.getClass()))
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
     * @param property new property to be added
     * @return true if it was added successfully, false otherwise
     */
    public boolean addProperty(C property)
    {
        Class<C> fieldPropertyClass = (Class<C>) property.getClass();
        if (hasProperty(fieldPropertyClass))
        {
            C alreadyDefinedProperty = getProperty(fieldPropertyClass);
            removeProperty(alreadyDefinedProperty);
        }
        return this.properties.add(property);
    }

    /**
     * Removes a property if exists in the current manager
     *
     * @param property
     * @return true if it was removed successfully, false otherwise
     */
    public boolean removeProperty(C property)
    {
        if (this.properties.contains(property))
        {
            return this.properties.remove(property);
        }
        return false;
    }

    /**
     * @return a unmodifiable list of properties
     */
    public List<C> getProperties()
    {
        return Collections.unmodifiableList(properties);
    }

    private void setProperties(List<C> properties)
    {
        if (hasRepeatedProperties(properties))
        {
            throw new RepeatedPropertyException("Properties must no be repeated");
        }
        this.properties = properties;
    }

    /**
     * As we want to ensure that two concrete objects of the same type will never be within the
     * {@link #properties} field, the comparison is by class name
     *
     * @param properties
     * @return true if there are two or more properties of the same class. False otherwise
     */
    private boolean hasRepeatedProperties(List<C> properties)
    {
        Set<Class<?>> clazzes = new HashSet<Class<?>>();
        for (C fc : properties)
        {
            clazzes.add(fc.getClass());
        }
        return clazzes.size() != properties.size();
    }
}
