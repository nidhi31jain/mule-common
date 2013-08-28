package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefinedMapMetaDataModel;

public interface PropertyCustomizableMetaDataBuilder<P extends MetaDataBuilder<?>> extends DynamicObjectFieldBuilder<P> {

	public abstract PropertyCustomizableMetaDataBuilder<P> isSelectCapable(boolean capable);

	public abstract PropertyCustomizableMetaDataBuilder<P> isOrderByCapable(boolean capable);

	public abstract CustomizingWhereMetaDataFieldBuilder<P> isWhereCapable(boolean capable);

}