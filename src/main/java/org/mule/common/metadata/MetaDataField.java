package org.mule.common.metadata;

public interface MetaDataField {
	
	public String getName();

	public MetaDataModel getMetaDataModel();

	public FieldAccessType getAccessType();

	public static enum FieldAccessType
	{
		READ, WRITE, READ_WRITE;
	}
}
