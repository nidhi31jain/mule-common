package org.mule.common.metadata;

import java.util.Set;

/**
 * Represents a POJO type.
 */
public interface PojoMetaDataModel extends StructuredMetaDataModel
{
     String getClassName();

     Set<String> getParentNames();

     boolean isInterface();
}
