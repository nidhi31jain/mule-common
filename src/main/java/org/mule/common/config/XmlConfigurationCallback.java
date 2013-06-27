
package org.mule.common.config;

import java.util.Map;

import org.w3c.dom.Element;

public interface XmlConfigurationCallback
{

    Element getGlobalElement(String globalElementName);

    String getSchemaLocation(String namespaceUri);

    Element[] getPropertyPlaceholders();

    Map<String, String> getEnvironmentProperties();
}
