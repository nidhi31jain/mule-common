package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;
import java.util.Set;

public class DefaultPojoMetaDataModel extends DefaultSimpleMetaDataModel implements PojoMetaDataModel {

    private Class<?> clazz;
	private String className;
	
	public DefaultPojoMetaDataModel(Class<?> clazz) {
		this(clazz.getSimpleName(), clazz.getName(), MetaDataModelFactory.getInstance().getParentNames(clazz));
	}
	
	public DefaultPojoMetaDataModel(String name, String className, Set<String> parentNames) {
		super(DataType.POJO, name, parentNames);
		this.className = className;
		try
        {
            this.clazz = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
            throw new IllegalArgumentException("must supply a valid class name", e);
        }
	}
	
	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public List<SimpleMetaDataModel> getFields() {
		return MetaDataModelFactory.getInstance().getFieldsForClass(clazz);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("DefaultPojoMetaDataModel { name=");
		sb.append(getName());
		sb.append(", className=");
		sb.append(className);
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
        result = prime * result + ((className == null) ? 0 : className.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultPojoMetaDataModel)) return false;
        DefaultPojoMetaDataModel other = (DefaultPojoMetaDataModel) obj;
        if (className == null)
        {
            if (other.className != null) return false;
        }
        else if (!className.equals(other.className)) return false;
        return true;
    }

}
