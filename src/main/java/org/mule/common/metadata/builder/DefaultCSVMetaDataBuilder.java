package org.mule.common.metadata.builder;


import org.mule.common.metadata.DefaultListMetaDataModel;
import org.mule.common.metadata.DefaultMetaDataField;
import org.mule.common.metadata.DefaultMetaDataFieldFactory;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.DefaultStructuredMetadataModel;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;
import org.mule.common.metadata.property.CSVHasHeadersMetaDataProperty;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

public class DefaultCSVMetaDataBuilder implements CSVMetaDataBuilder
{

    private List<MetaDataField> fields;
    private boolean hasHeaders;
    private String example;

    public DefaultCSVMetaDataBuilder()
    {
        fields = new ArrayList<MetaDataField>();
        hasHeaders = true;
    }

    @Override
    public CSVMetaDataBuilder addField(String fieldName, DataType type)
    {
        return addField(fieldName, type, Collections.<MetaDataFieldProperty>emptyList());
    }

    @Override
    public CSVMetaDataBuilder addField(String fieldName, DataType type, List<MetaDataFieldProperty> fieldProperties)
    {
        Objects.requireNonNull(fieldProperties, "fieldProperties must not be null");
        fields.add(new DefaultMetaDataField(fieldName, new DefaultSimpleMetaDataModel(type), MetaDataField.FieldAccessType.READ, fieldProperties));
        return this;
    }

    @Override
    public CSVMetaDataBuilder setHasHeaders(boolean hasHeaders)
    {
        this.hasHeaders = hasHeaders;
        return this;
    }

    @Override
    public CSVMetaDataBuilder setExample(String example)
    {
        this.example = example;
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
        metadataModel.addProperty(new CSVHasHeadersMetaDataProperty(hasHeaders));
        if (StringUtils.isNotBlank(example))
        {
            metadataModel.addProperty(new TextBasedExampleMetaDataModelProperty(example));
        }
        return new DefaultListMetaDataModel(metadataModel);
    }
}
