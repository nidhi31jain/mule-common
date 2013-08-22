package org.mule.common.metadata.builder;

import org.mule.common.metadata.PojoMetaDataModel;
import org.mule.common.metadata.field.property.FieldPropertyFactory;

/**
 *
 */
public interface PojoMetaDataBuilder<P extends MetaDataBuilder> extends MetaDataBuilder<PojoMetaDataModel>
{

    PojoMetaDataBuilder usingFieldPropertyFactory(FieldPropertyFactory factory);

    P endPojo();
}
