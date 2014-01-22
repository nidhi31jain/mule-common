package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

/**
 * Property used to specify a description of a field.
 */
public class DescriptionMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty
{

    private String description;

    public DescriptionMetaDataProperty(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
