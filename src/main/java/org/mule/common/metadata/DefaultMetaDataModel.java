package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

public class DefaultMetaDataModel implements MetaDataModel {

	private DataType dataType;
	
	public DefaultMetaDataModel(DataType dataType) {
		this.dataType = dataType;
	}
	
	@Override
	public DataType getDataType() {
		return dataType;
	}

	@Override
	public <T extends MetaDataModel> T as(Class<T> clazz) {

        if (this.getClass().isAssignableFrom(clazz))
        {
            return clazz.cast(this);
        }
        return null;
	}

	@Override
	public String toString()
	{
	    return "DefaultMetaDataModel:{ dataType:" + dataType.toString() + " }";
	}

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof DefaultMetaDataModel)) return false;
        DefaultMetaDataModel other = (DefaultMetaDataModel) obj;
        if (dataType != other.dataType) return false;
        return true;
    }
}
