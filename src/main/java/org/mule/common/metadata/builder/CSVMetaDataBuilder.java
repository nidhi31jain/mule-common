package org.mule.common.metadata.builder;

import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.StructuredMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public interface CSVMetaDataBuilder extends MetaDataBuilder<ListMetaDataModel>
{
    CSVMetaDataBuilder addField(String fieldName, DataType type);
}
