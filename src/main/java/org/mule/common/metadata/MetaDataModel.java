package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

public interface MetaDataModel
{
	public DataType getDataType();
	public <T extends MetaDataModel> T as(Class<T> clazz);

    public void accept(MetaDataModelVisitor modelVisitor);
}
