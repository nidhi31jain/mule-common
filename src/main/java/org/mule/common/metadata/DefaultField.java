package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

public class DefaultField implements Field {

	private String name;
	private DataType dataType;
	
	public DefaultField(String name, DataType dataType) {
		this.name = name;
		this.dataType = dataType;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public DataType getDataType() {
		return dataType;
	}

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dataType == null) ? 0 : dataType.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof DefaultField)) return false;
        DefaultField other = (DefaultField) obj;
        if (dataType != other.dataType) return false;
        if (name == null)
        {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }
}
