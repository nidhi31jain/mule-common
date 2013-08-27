package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataModel;

public interface PropertyCustomizableMetaDataBuilder extends MetaDataBuilder<DefinedMapMetaDataModel> {

	public abstract MetaDataFieldBuilder isSelectCapable(boolean capable);

	public abstract MetaDataFieldBuilder isOrderByCapable(boolean capable);

	public abstract CustomizingWhereMetaDataFieldBuilder isWhereCapable(boolean capable);

}