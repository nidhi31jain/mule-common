package org.mule.common.metadata;

public interface CSVMetaDataModel extends StructuredMetaDataModel
{
    String getExample();

    void setExample(String example);

    void setDelimiter(String delimiter);

    String getDelimiter();
}
