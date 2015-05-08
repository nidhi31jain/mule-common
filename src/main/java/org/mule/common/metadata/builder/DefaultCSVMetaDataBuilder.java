package org.mule.common.metadata.builder;


import org.apache.commons.lang.StringUtils;
import org.mule.common.DefaultCSVMetaDataModel;
import org.mule.common.metadata.CSVMetaDataModel;
import org.mule.common.metadata.DefaultMetaDataField;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class DefaultCSVMetaDataBuilder implements CSVMetaDataBuilder
{
    private String csv;
    private String delimiter;
    private List<MetaDataField> fields;

    public DefaultCSVMetaDataBuilder()
    {
        fields = new ArrayList<MetaDataField>();
    }

    @Override
    public CSVMetaDataBuilder setExample(String csv)
    {
        this.csv = csv;
        return this;
    }

    @Override
    public CSVMetaDataBuilder setDelimiter(String delimiter)
    {
        this.delimiter = delimiter;
        return this;
    }

    @Override
    public CSVMetaDataBuilder addField(String fieldName, DataType type)
    {
        fields.add(new DefaultMetaDataField(fieldName, new DefaultSimpleMetaDataModel(type), MetaDataField.FieldAccessType.READ));
        return this;
    }

    @Override
    public CSVMetaDataModel build()
    {
        CSVMetaDataModel model = null;

        if (isNotBlank(delimiter) && !fields.isEmpty())
        {
            model = new DefaultCSVMetaDataModel(fields);
            model.setDelimiter(delimiter);

            if(isNotBlank(csv))
            {
                model.setExample(csv);
            }
        }

        return model;
    }
}
