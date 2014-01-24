package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;

public abstract class AbstractMetaDataModel implements MetaDataModel
{

    private String implementationClass;
    private DataType dataType;
    private MetaDataPropertyManager<MetaDataModelProperty> metaDataModelPropertiesManager;

    protected AbstractMetaDataModel(DataType dataType)
    {
        this.dataType = dataType;
        this.metaDataModelPropertiesManager = new MetaDataPropertyManager<MetaDataModelProperty>();
    }

    @Override
    public DataType getDataType()
    {
        return dataType;
    }

    @Override
    public <T extends MetaDataModel> T as(Class<T> clazz)
    {
        if ((clazz.isAssignableFrom(this.getClass())))
        {
            return clazz.cast(this);
        }
        return null;
    }

    protected void addAllProperties(MetaDataModelProperty[] properties)
    {
        for (MetaDataModelProperty property : properties)
        {
            addProperty(property);
        }
    }

    @Override
    public List<MetaDataModelProperty> getProperties()
    {
        return this.metaDataModelPropertiesManager.getProperties();
    }

    @Override
    public boolean addProperty(MetaDataModelProperty metaDataModelProperty)
    {
        return this.metaDataModelPropertiesManager.addProperty(metaDataModelProperty);
    }

    @Override
    public boolean removeProperty(MetaDataModelProperty metaDataModelProperty)
    {
        return this.metaDataModelPropertiesManager.removeProperty(metaDataModelProperty);
    }

    @Override
    public boolean hasProperty(Class<? extends MetaDataModelProperty> metaDataModelProperty)
    {
        return this.metaDataModelPropertiesManager.hasProperty(metaDataModelProperty);
    }

    @Override
    public <T extends MetaDataModelProperty> T getProperty(Class<T> metaDataModelProperty)
    {
        return this.metaDataModelPropertiesManager.getProperty(metaDataModelProperty);
    }

    @Override
    public String getImplementationClass()
    {
        return implementationClass != null ? implementationClass
                                           : inferImplementationClass();
    }

    private String inferImplementationClass()
    {
        return dataType.getDefaultImplementationClass();
    }

    public void setImplementationClass(String implementationClass)
    {
        this.implementationClass = implementationClass;
    }

    @Override
    public String toString()
    {
        return "DefaultMetaDataModel:{ dataType:" + dataType != null ? dataType
                .toString() : "null" + " }";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result
                 + ((dataType == null) ? 0 : dataType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof AbstractMetaDataModel))
        {
            return false;
        }
        AbstractMetaDataModel other = (AbstractMetaDataModel) obj;
        if (dataType != other.dataType)
        {
            return false;
        }
        return true;
    }
}
