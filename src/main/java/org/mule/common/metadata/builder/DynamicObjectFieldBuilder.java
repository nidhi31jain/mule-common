package org.mule.common.metadata.builder;

import org.mule.common.metadata.MetaDataField;

public interface DynamicObjectFieldBuilder<P extends MetaDataBuilder<?>> extends DynamicObjectBuilder<P> {

    DynamicObjectFieldBuilder<P> withAccessType(MetaDataField.FieldAccessType accessType);

}
