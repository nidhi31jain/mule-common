package org.mule.common.metadata.property.xml;

import org.mule.common.metadata.field.property.MetaDataFieldProperty;

/**
 * Created by machaval on 1/26/14.
 */
public class AttributeMetaDataFieldProperty implements MetaDataFieldProperty
{

    private boolean isAttribute;

    public AttributeMetaDataFieldProperty(boolean isAttribute)
    {
        this.isAttribute = isAttribute;
    }

    public boolean isAttribute()
    {
        return isAttribute;
    }
}
