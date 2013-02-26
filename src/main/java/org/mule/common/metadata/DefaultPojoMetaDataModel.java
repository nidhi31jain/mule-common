package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;

public class DefaultPojoMetaDataModel extends DefaultSimpleMetaDataModel implements PojoMetaDataModel {

    private Class<?> clazz;
	
	public DefaultPojoMetaDataModel(Class<?> clazz) {
		this(clazz, clazz.getSimpleName());
	}
	
	public DefaultPojoMetaDataModel(Class<?> clazz, String name) {
        super(DataType.POJO, name,  MetaDataModelFactory.getInstance().getParentNames(clazz));
        this.clazz = clazz;
    }
	
	@Override
	public String getClassName() {
		return clazz.getName();
	}

	@Override
	public List<SimpleMetaDataModel> getFields() {
		return MetaDataModelFactory.getInstance().getFieldsForClass(clazz);
	}

    @Override
    public boolean isInterface()
    {
        return clazz.isInterface();
    }

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("DefaultPojoMetaDataModel { name=");
		sb.append(getName());
		sb.append(", className=");
		sb.append(clazz.getName());
		sb.append(", fields=");
		List<SimpleMetaDataModel> fields = getFields();
		if (fields != null) {
			sb.append("[");
			for (SimpleMetaDataModel f : fields) {
			    sb.append(f.getName());
			    sb.append("={ dataType=");
		        sb.append(f.getDataType());
			    sb.append("},");
			}
			sb.append("]");
		} else {
			sb.append("null");
		}
		sb.append(" }");
		return  sb.toString();
	}

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((clazz.getName() == null) ? 0 : clazz.getName().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultPojoMetaDataModel)) return false;
        DefaultPojoMetaDataModel other = (DefaultPojoMetaDataModel) obj;
        if (clazz.getName() == null)
        {
            if (other.clazz.getName() != null) return false;
        }
        else if (!clazz.getName().equals(other.clazz.getName())) return false;
        return true;
    }

}
