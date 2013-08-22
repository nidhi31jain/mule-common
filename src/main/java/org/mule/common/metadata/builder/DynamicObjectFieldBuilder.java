package org.mule.common.metadata.builder;


import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.field.property.DsqlMetaDataFieldProperty;

/**
 *
 */
public interface DynamicObjectFieldBuilder<P extends MetaDataBuilder<?>> extends DynamicObjectBuilder<P>
{

    DynamicObjectFieldBuilder<P> withDsqlProperty(DsqlMetaDataFieldProperty... properties);

    DynamicObjectFieldBuilder<P> withAccessType(MetaDataField.FieldAccessType accessType);

    DynamicObjectFieldBuilder<P> withImplClass(String className);
}
