package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;


public class DefaultStructuredMetadataModel extends AbstractMetaDataModel implements StructuredMetaDataModel {

    private List<MetaDataField> fields;

    public DefaultStructuredMetadataModel(DataType dataType) {
        super(dataType);
    }

    public DefaultStructuredMetadataModel(DataType dataType, MetaDataFieldFactory fieldFactory) {
        this(dataType);
        loadFieldsFrom(fieldFactory);
    }

    public void loadFieldsFrom(MetaDataFieldFactory fieldFactory) {
        fields = fieldFactory.createFields();
    }


    @Override
    public void accept(MetaDataModelVisitor modelVisitor) {
        //TODO we should deprecate visitor of MetadataModel
    }

    @Override
    public List<MetaDataField> getFields() {
        return fields;
    }

    @Override
    public MetaDataField getFieldByName(String name) {
        for (MetaDataField field : fields) {
            if (field.getName().equals(name)) {
                return field;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        DefaultStructuredMetadataModel that = (DefaultStructuredMetadataModel) o;

        if (fields != null ? !fields.equals(that.fields) : that.fields != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fields != null ? fields.hashCode() : 0);
        return result;
    }
}
