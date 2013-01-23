package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;
import java.util.Set;

public class DefaultPojoMetaDataModel extends DefaultSimpleMetaDataModel implements PojoMetaDataModel {

	private String className;
	private List<SimpleMetaDataModel> fields;
	
	@SuppressWarnings("rawtypes")
	public DefaultPojoMetaDataModel(Class clazz) {
		this(clazz.getSimpleName(), clazz.getName(), MetaDataModelFactory.getInstance().getParentNames(clazz), 
		        MetaDataModelFactory.getInstance().getFieldsForClass(clazz));
	}

    public DefaultPojoMetaDataModel(String name, String className, Set<String> parentNames, List<SimpleMetaDataModel> fields) {
		super(DataType.POJO, name, parentNames);
		this.className = className;
		this.fields = fields;
	}
	
	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public List<SimpleMetaDataModel> getFields() {
		return fields;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("DefaultPojoMetaDataModel { name=");
		sb.append(getName());
		sb.append(", className=");
		sb.append(className);
		sb.append(", fields=");
		if (fields != null) {
			sb.append("[");
			for (SimpleMetaDataModel f : fields) {
			    sb.append("{");
			    sb.append(f.toString());
			    sb.append("}");
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
        result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
        if (fields == null)
        {
            if (other.fields != null) return false;
        }
        else if (!fields.equals(other.fields)) return false;
        return true;
    }

}
