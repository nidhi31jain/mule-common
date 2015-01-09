package org.mule.common.metadata;

import java.io.InputStream;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlException;

public interface SchemaProvider
{

    List<InputStream> getSchemas();

    SchemaGlobalElement findRootElement(QName rootElementName) throws XmlException;

    SchemaType findRootType(QName rootElementName) throws XmlException;
}
