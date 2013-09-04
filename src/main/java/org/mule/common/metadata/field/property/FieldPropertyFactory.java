package org.mule.common.metadata.field.property;

import java.util.List;

import org.mule.common.metadata.MetaDataModel;

/**
 *
 */
public interface FieldPropertyFactory {

    List<MetaDataFieldProperty> getProperties(String name, MetaDataModel metaDataModel);
    
    List<MetaDataFieldProperty> getProperties(Class<?> clazz);
}
