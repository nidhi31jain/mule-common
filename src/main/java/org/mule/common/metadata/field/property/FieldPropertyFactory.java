package org.mule.common.metadata.field.property;

import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.exception.NoImplementationClassException;

import java.util.List;

/**
 *
 */
public interface FieldPropertyFactory
{

    List<MetaDataFieldProperty> getProperties(String name, MetaDataModel metaDataModel) throws NoImplementationClassException;
}
