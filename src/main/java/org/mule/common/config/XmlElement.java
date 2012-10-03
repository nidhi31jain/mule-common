
package org.mule.common.config;

import java.util.Map;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

public interface XmlElement
{
    String getName();

    QName getElementName();

    Map<String, String> getAttributes();

    String getAttribute(final String key);
    
    Element getXml();
}
