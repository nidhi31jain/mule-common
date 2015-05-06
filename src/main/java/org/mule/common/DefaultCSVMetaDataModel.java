package org.mule.common;

import org.mule.common.metadata.AbstractStructuredMetaDataModel;
import org.mule.common.metadata.CSVMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModelVisitor;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.CSVDelimiterMetaDataModelProperty;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import java.util.List;

public class DefaultCSVMetaDataModel extends AbstractStructuredMetaDataModel implements CSVMetaDataModel
{
    public DefaultCSVMetaDataModel(List<MetaDataField> fields)
    {
        super(DataType.CSV, fields);
    }

    @Override
    public String getExample()
    {
        if (hasProperty(TextBasedExampleMetaDataModelProperty.class))
        {
            return getProperty(TextBasedExampleMetaDataModelProperty.class).getExampleContent();
        }
        return null;
    }

    @Override
    public void setExample(String xmlExample)
    {
        addProperty(new TextBasedExampleMetaDataModelProperty(xmlExample));
    }

    @Override
    public String getDelimiter()
    {
        if (hasProperty(CSVDelimiterMetaDataModelProperty.class))
        {
            return getProperty(CSVDelimiterMetaDataModelProperty.class).getDelimiter();
        }

        return null;
    }

    public void setDelimiter(String delimiter)
    {
        addProperty(new CSVDelimiterMetaDataModelProperty(delimiter));
    }

    @Override
    public void accept(MetaDataModelVisitor modelVisitor)
    {
    }
}
