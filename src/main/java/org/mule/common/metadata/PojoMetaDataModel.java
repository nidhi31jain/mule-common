package org.mule.common.metadata;

import java.util.List;

public interface PojoMetaDataModel extends SimpleMetaDataModel
{	
	public String getClassName();
	public List<Field> getFields();
}
