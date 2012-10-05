
package org.mule.common.config;

import org.w3c.dom.Element;

public interface XmlConfigurationCallback
{
    Element getGlobalElement(final String globalElementName);

    String getSchemaLocation(String namespaceUri);

}
