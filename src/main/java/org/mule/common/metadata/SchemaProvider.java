package org.mule.common.metadata;

import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.XmlException;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface SchemaProvider {

    List<InputStream> getSchemas();

    SchemaGlobalElement findRootElement(QName rootElementName) throws XmlException;

}
