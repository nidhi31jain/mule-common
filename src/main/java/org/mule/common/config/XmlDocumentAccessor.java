
package org.mule.common.config;

import java.util.List;

import javax.xml.namespace.QName;

public interface XmlDocumentAccessor
{
    XmlElement getGlobalElement(final String globalElementName);

    List<XmlElement> getGlobalElements(QName name);

}
