package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.DataTypeFactory;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultPojoMetaDataModel extends DefaultSimpleMetaDataModel implements PojoMetaDataModel {

	private String className;
	private List<Field> fields;
	
	@SuppressWarnings("rawtypes")
	public DefaultPojoMetaDataModel(Class clazz) {
		this(clazz.getSimpleName(), clazz.getName(), getParentsForClass(clazz), getFieldsForClass(clazz));
	}

    public DefaultPojoMetaDataModel(String name, String className, Set<String> parentNames, List<Field> fields) {
		super(DataType.POJO, name, parentNames);
		this.className = className;
		this.fields = fields;
	}

    @SuppressWarnings("rawtypes")
    private static Set<String> getParentsForClass(Class clazz)
    {
        Set<String> parents = new HashSet<String>();
        for (Class c : clazz.getInterfaces()) {
            if (c != null)
            {
                parents.add(c.getCanonicalName());
                parents.addAll(getParentsForClass(c));
            }
        }
        Class parent = clazz.getSuperclass();
        if (parent != null)
        {
            parents.add(parent.getCanonicalName());
            parents.addAll(getParentsForClass(parent));
        }
        return parents;
    }
	
	@SuppressWarnings("rawtypes")
	private static List<Field> getFieldsForClass(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
		    if (!Modifier.isStatic(f.getModifiers())) {
    			String name = f.getName();
    			DataType dataType = DataTypeFactory.getInstance().getDataType(f.getType());
    			fields.add(new DefaultField(name, dataType));
		    }
		}
		return fields;
	}
	
	@Override
	public String getClassName() {
		return className;
	}

	@Override
	public List<Field> getFields() {
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
			for (Field f : fields) {
				sb.append("{name=");
				sb.append(f.getName());
				sb.append(", dataType=");
				sb.append(f.getDataType().toString());
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
