package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;

public interface MetaDataModel
{

    DataType getDataType();

    <T extends MetaDataModel> T as(Class<T> clazz);

    void accept(MetaDataModelVisitor modelVisitor);

    String getImplementationClass();

    List<MetaDataModelProperty> getProperties();

    boolean addProperty(MetaDataModelProperty metaDataFieldProperty);

    boolean removeProperty(MetaDataModelProperty metaDataFieldProperty);

    boolean hasProperty(Class<? extends MetaDataModelProperty> metaDataFieldProperty);

    <T extends MetaDataModelProperty> T getProperty(Class<T> metaDataFieldProperty);
}
