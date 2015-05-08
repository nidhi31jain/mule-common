package org.mule.common.metadata.builder;

import org.mule.common.metadata.CSVMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public interface CSVMetaDataBuilder extends MetaDataBuilder<CSVMetaDataModel>
{
    CSVMetaDataBuilder setExample(String xmlExample);

    CSVMetaDataBuilder setDelimiter(String delimiter);

    CSVMetaDataBuilder addField(String fieldName, DataType type);
}
