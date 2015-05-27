package org.mule.common.metadata.property.xml;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;


public class UnboundMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty
{

    private int maxOccurs;
    private int minOccurs;

    public UnboundMetaDataProperty(int minOccurs, int maxOccurs)
    {
        this.minOccurs = minOccurs;
        this.maxOccurs = maxOccurs;
    }


    public int getMaxOccurs()
    {
        return maxOccurs;
    }

    public int getMinOccurs()
    {
        return minOccurs;
    }
}
