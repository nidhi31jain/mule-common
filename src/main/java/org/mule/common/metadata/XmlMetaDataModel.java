package org.mule.common.metadata;

import java.io.InputStream;
import java.util.List;

import javax.xml.namespace.QName;

/**
 * Represents an XML Object.
 */
public interface XmlMetaDataModel extends StructuredMetaDataModel
{
    List<InputStream> getSchemas();

    String getExample();
    
    void setExample(String xmlExample);
    
    QName getRootElement();
}
