package org.mule.common.metadata.builder;


import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.field.property.DsqlMetaDataFieldProperty;

/**
 *
 */
public interface DynamicObjectFieldBuilder<P extends MetaDataBuilder<?>> extends DynamicObjectBuilder<P>, PropertyCustomizableMetaDataBuilder, MetaDataFieldBuilder
{

    DynamicObjectFieldBuilder<P> withAccessType(MetaDataField.FieldAccessType accessType);
    
}
