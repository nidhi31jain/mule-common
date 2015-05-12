package org.mule.common.metadata.builder;


import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaDataField;
import org.mule.common.metadata.DefaultMetaDataFieldFactory;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.DefaultStructuredMetadataModel;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataFieldFactory;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.List;

public class DefaultCSVMetaDataBuilder implements CSVMetaDataBuilder
{

    private List<MetaDataField> fields;

    public DefaultCSVMetaDataBuilder()
    {
        fields = new ArrayList<MetaDataField>();
    }


    @Override
    public CSVMetaDataBuilder addField(String fieldName, DataType type)
    {
        fields.add(new DefaultMetaDataField(fieldName, new DefaultSimpleMetaDataModel(type), MetaDataField.FieldAccessType.READ));
        return this;
    }

    @Override
    public ListMetaDataModel build()
    {
        if (fields.isEmpty())
        {
            throw new IllegalStateException("At least one field should be declared");
        }
        final DefaultStructuredMetadataModel metadataModel = new DefaultStructuredMetadataModel(DataType.CSV, new DefaultMetaDataFieldFactory(fields));
        return new DefaultListMetaDataModel(metadataModel);
    }
}
