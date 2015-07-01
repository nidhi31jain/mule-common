package org.mule.common.metadata.property.xml;

import org.mule.common.metadata.MetaDataModelProperty;

import javax.xml.namespace.QName;

/**
 * Contains the qname of schema type of the metadata model.
 */
public class SchemaTypeMetaDataProperty implements MetaDataModelProperty
{

    private QName typeQName;

    public SchemaTypeMetaDataProperty(QName typeQName)
    {
        this.typeQName = typeQName;
    }

    public QName getTypeQName()
    {
        return typeQName;
    }
}
