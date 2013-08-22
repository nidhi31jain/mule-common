package org.mule.common.metadata.field.property;

import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.exception.NoImplementationClassException;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

import java.util.List;

/**
 *
 */
public interface FieldPropertyFactory {
    public List<MetaDataFieldProperty> getCapabilities(String name, MetaDataModel metaDataModel) throws NoImplementationClassException;
}
