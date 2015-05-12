package org.mule.common.metadata;

import java.util.List;


public class DefaultMetaDataFieldFactory implements MetaDataFieldFactory
{

    private List<MetaDataField> fields;

    public DefaultMetaDataFieldFactory(List<MetaDataField> fields)
    {
        this.fields = fields;
    }

    @Override
    public List<MetaDataField> createFields()
    {
        return this.fields;
    }
}
