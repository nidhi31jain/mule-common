package org.mule.metadata.api;

import java.util.Map;

public interface MuleXmlElement 
{
	String getNamespace();
	
	String getName();
	
	String getElementName();
	
	Map<String, String> getAttributes();
	
	String getAttribute(final String key);
}
