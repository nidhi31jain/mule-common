package org.mule.common.metadata.builder;

import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

import java.util.List;

public interface CSVMetaDataBuilder extends MetaDataBuilder<ListMetaDataModel>
{
    CSVMetaDataBuilder addField(String fieldName, DataType type);

    CSVMetaDataBuilder addField(String fieldName, DataType type, List<MetaDataFieldProperty> fieldProperties);

    CSVMetaDataBuilder setHasHeaders(boolean hasHeaders);

    CSVMetaDataBuilder setExample(String example);
}
