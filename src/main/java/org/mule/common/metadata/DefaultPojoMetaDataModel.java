package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.Collections;
import java.util.List;

public class DefaultPojoMetaDataModel extends DefaultSimpleMetaDataModel implements PojoMetaDataModel {

    private String clazzName;
    private boolean isInterface;
    private List<MetaDataModel> fieldsForClass;
	
	public DefaultPojoMetaDataModel(Class<?> clazz) {
		this(clazz, clazz.getSimpleName());
	}
	
	public DefaultPojoMetaDataModel(Class<?> clazz, String name) {
        super(DataType.POJO, name,  MetaDataModelFactory.getInstance().getParentNames(clazz));
        this.clazzName = clazz.getName();
        this.isInterface = clazz.isInterface();
        this.fieldsForClass = MetaDataModelFactory.getInstance().getFieldsForClass(clazz);
    }
	
	@Override
	public String getClassName() {
		return clazzName;
	}

	@Override
	public List<MetaDataModel> getFields() {
		return fieldsForClass;
	}

    @Override
    public boolean isInterface()
    {
        return isInterface;
    }

    @Override
    public String toString()
    {
        return "DefaultPojoMetaDataModel{" +
                "clazzName='" + clazzName + '\'' +
                ", isInterface=" + isInterface +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof DefaultPojoMetaDataModel))
        {
            return false;
        }
        if (!super.equals(o))
        {
            return false;
        }

        DefaultPojoMetaDataModel that = (DefaultPojoMetaDataModel) o;

        if (isInterface != that.isInterface)
        {
            return false;
        }
        if (clazzName != null ? !clazzName.equals(that.clazzName) : that.clazzName != null)
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = 31 * result + (clazzName != null ? clazzName.hashCode() : 0);
        result = 31 * result + (isInterface ? 1 : 0);
        return result;
    }
}
