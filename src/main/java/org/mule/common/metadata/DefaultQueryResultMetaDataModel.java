package org.mule.common.metadata;

import java.util.List;
import java.util.Set;

import org.mule.common.metadata.datatype.DataType;

/**
 * <p>Query metadata representation</p>
 * <p>Shouldn't use this directly. Use {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} instead.</p>
 */

public class DefaultQueryResultMetaDataModel implements QueryResultMetaDataModel
{

    private DefinedMapMetaDataModel definedMapMetaDataModel;

    public DefaultQueryResultMetaDataModel(DefinedMapMetaDataModel definedMapMetaDataModel)
    {
        this.definedMapMetaDataModel = definedMapMetaDataModel;
    }

    @Override
    public String getName()
    {
        return definedMapMetaDataModel.getName();
    }

    @Override
    public Set<String> getKeys() {
        return definedMapMetaDataModel.getKeys();
    }

    @Override
    public MetaDataModel getKeyMetaDataModel()
    {
        return definedMapMetaDataModel.getKeyMetaDataModel();
    }

    @Override
    public MetaDataModel getValueMetaDataModel(String key)
    {
        return definedMapMetaDataModel.getValueMetaDataModel(key);
    }

    @Override
    public List<MetaDataField> getFields() {
        return definedMapMetaDataModel.getFields();
    }

    @Override
    public DataType getDataType()
    {
        return definedMapMetaDataModel.getDataType();
    }

    @Override
    public <T extends MetaDataModel> T as(Class<T> clazz) {
        if ((clazz.isAssignableFrom(this.getClass())))
        {
            return clazz.cast(this);
        }
        return null;
    }

    @Override
    public void accept(MetaDataModelVisitor modelVisitor)
    {
        modelVisitor.visitDynamicMapModel(this);
    }

	@Override
	public String getImplementationClass() {
		return definedMapMetaDataModel.getImplementationClass();
	}

    @Override
    public List<MetaDataModelProperty> getProperties()
    {
        return definedMapMetaDataModel.getProperties();
    }

    @Override
    public boolean addProperty(MetaDataModelProperty metaDataFieldProperty)
    {
        return definedMapMetaDataModel.addProperty(metaDataFieldProperty);
    }

    @Override
    public boolean removeProperty(MetaDataModelProperty metaDataFieldProperty)
    {
        return definedMapMetaDataModel.removeProperty(metaDataFieldProperty);
    }

    @Override
    public boolean hasProperty(Class<? extends MetaDataModelProperty> metaDataFieldProperty)
    {
        return definedMapMetaDataModel.hasProperty(metaDataFieldProperty);
    }

    @Override
    public <T extends MetaDataModelProperty> T getProperty(Class<T> metaDataFieldProperty)
    {
        return definedMapMetaDataModel.getProperty(metaDataFieldProperty);
    }

}
