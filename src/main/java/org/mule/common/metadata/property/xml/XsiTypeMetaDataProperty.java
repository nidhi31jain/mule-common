package org.mule.common.metadata.property.xml;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

import javax.xml.namespace.QName;

public class XsiTypeMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty {
    private QName name;

    public XsiTypeMetaDataProperty(QName name)
    {
        this.name = name;
    }

    public QName getName()
    {
        return name;
    }
}
