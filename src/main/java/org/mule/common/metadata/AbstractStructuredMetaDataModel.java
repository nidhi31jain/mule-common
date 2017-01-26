package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.Collections;
import java.util.List;


public abstract class AbstractStructuredMetaDataModel extends AbstractMetaDataModel implements StructuredMetaDataModel {

    private List<MetaDataField> fields;
    private boolean allowsAnyFields;


    protected AbstractStructuredMetaDataModel(DataType dataType, MetaDataFieldFactory fieldFactory) throws Exception {
        super(dataType);
        this.fields = fieldFactory.createFields();
        this.allowsAnyFields = false;
    }

    protected AbstractStructuredMetaDataModel(DataType dataType, List<MetaDataField> fields)
    {
        super(dataType);
        this.fields = fields;
        this.allowsAnyFields = false;
    }

    protected AbstractStructuredMetaDataModel(DataType dataType, List<MetaDataField> fields, boolean allowsAnyFields)
    {
        super(dataType);
        this.fields = fields;
        this.allowsAnyFields = allowsAnyFields;
    }

    @Override
    public List<MetaDataField> getFields() {
        return Collections.unmodifiableList(fields);
    }

    @Override
    public MetaDataField getFieldByName(String name) {
        for (MetaDataField field : getFields()) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public boolean isAnyFieldAllowed()
    {
        return this.allowsAnyFields;
    }
}
