package org.mule.common.metadata;


import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlException;

import javax.xml.namespace.QName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlMetaDataTypeFieldFactory extends XmlMetaDataFieldFactory
{

    public XmlMetaDataTypeFieldFactory(SchemaProvider schemas, QName typeElementName, XmlMetaDataNamespaceManager namespaceManager)
    {
        super(schemas, typeElementName, namespaceManager);
    }

    public SchemaType getRootType()
    {
        try
        {
            return getSchemas().findRootType(getRootElementName());
        }
        catch (XmlException e)
        {
            throw new MetaDataGenerationException(e);
        }
    }

}
