package org.mule.common.metadata;

import java.util.List;
import java.util.Set;

/**
 * Represents a POJO type.
 */
public interface PojoMetaDataModel extends MetaDataModel
{
    public String getClassName();

    public List<MetaDataField> getFields();
    
    public Set<String> getParentNames();

    public boolean isInterface();
}
