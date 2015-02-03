package org.mule.common.metadata.builder;

import org.mule.common.metadata.MetaDataModel;

public interface JSONMetaDataBuilder<P extends MetaDataBuilder<?>> extends MetaDataBuilder<MetaDataModel> {

	public JSONMetaDataBuilder<P> setSchema(String jsonSchema);

	public JSONMetaDataBuilder<P> setExample(String jsonExample);
}
