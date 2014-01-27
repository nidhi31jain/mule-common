package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

import javax.xml.namespace.QName;


public class QNameMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty
{

    private QName name;

    public QNameMetaDataProperty(QName name)
    {
        this.name = name;
    }

    public QName getName()
    {
        return name;
    }
}
