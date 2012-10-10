
package org.mule.common.config;

import org.w3c.dom.Element;

public interface XmlConfigurationCallback
{
    Element getGlobalElement(String globalElementName);

    String getSchemaLocation(String namespaceUri);
}
