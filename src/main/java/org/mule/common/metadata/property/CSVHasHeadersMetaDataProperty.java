package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;


public class CSVHasHeadersMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty
{
    private boolean hasHeaders;

    public CSVHasHeadersMetaDataProperty(boolean hasHeaders)
    {
        this.hasHeaders = hasHeaders;
    }

    public boolean hasHeaders()
    {
        return hasHeaders;
    }

    public void setHasHeaders(boolean hasHeaders)
    {
        this.hasHeaders = hasHeaders;
    }
}
