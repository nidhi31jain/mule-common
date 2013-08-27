/**
 *
 */
package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public class DefaultSimpleMetaDataBuilder implements SimpleMetaDataBuilder
{

    private DataType dataType;
	private String implClass;

    DefaultSimpleMetaDataBuilder(DataType dataType)
    {
        this.dataType = dataType;
    }

    @Override
    public MetaDataModel build()
    {
        DefaultSimpleMetaDataModel defaultSimpleMetaDataModel = new DefaultSimpleMetaDataModel(dataType);
        defaultSimpleMetaDataModel.setImplementationClass(implClass == null? dataType.getDefaultImplementationClass() : implClass);
		return defaultSimpleMetaDataModel;
    }

	@Override
	public DataType getDataType() {
		return dataType;
	}

	public void setImplClass(String implClass) {
		this.implClass = implClass;
		
	}
}
