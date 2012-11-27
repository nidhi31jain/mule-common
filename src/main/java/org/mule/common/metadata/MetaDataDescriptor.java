package org.mule.common.metadata;

public interface MetaDataDescriptor
{
	public MetaData getInputMetaData();
	public MetaData getOutputMetaData(MetaData inputMetaData);
}
