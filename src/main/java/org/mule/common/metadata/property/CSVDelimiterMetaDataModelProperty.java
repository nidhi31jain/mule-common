package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;

public class CSVDelimiterMetaDataModelProperty implements MetaDataModelProperty
{
    private final String delimiter;

    public CSVDelimiterMetaDataModelProperty(String delimiter)
    {
        this.delimiter = delimiter;
    }

    public String getDelimiter()
    {
        return delimiter;
    }
}
