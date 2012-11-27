package org.mule.common.metadata;

import java.util.Set;

public interface MapMetaDataModel<K> extends MetaDataModel
{
	public MetaDataModel getKeyModel();
	public Set<K> getKeys();
	public MetaDataModel getValueModel(K key);
}
