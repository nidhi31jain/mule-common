package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataModel;

public interface PropertyCustomizableMetaDataBuilder extends MetaDataBuilder<DefinedMapMetaDataModel> {

	public abstract DynamicObjectBuilder isSelectCapable(boolean capable);

	public abstract DynamicObjectBuilder isOrderByCapable(boolean capable);

	public abstract CustomizingWhereMetaDataFieldBuilder isWhereCapable(boolean capable);

}