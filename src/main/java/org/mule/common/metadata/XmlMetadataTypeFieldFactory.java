package org.mule.common.metadata;


import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlException;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlMetaDataTypeFieldFactory extends XmlMetaDataFieldFactory {

    public XmlMetaDataTypeFieldFactory(SchemaProvider schemas, QName typeElementName)
    {
        super(schemas,typeElementName);
    }

    @Override
    public List<MetaDataField> createFields()
    {
        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();
        final Map<SchemaType, XmlMetaDataModel> visitedTypes = new HashMap<SchemaType, XmlMetaDataModel>();
        try {
            SchemaType rootType = getSchemas().findRootType(getRootElementName());
            if (rootType != null) {
                loadFields(rootType, metaDataFields, visitedTypes);
            }
        }
        catch (XmlException e)
        {
            throw new MetaDataGenerationException(e);
        }

        return metaDataFields;
    }

}
