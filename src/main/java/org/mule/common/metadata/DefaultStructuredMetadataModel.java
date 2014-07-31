package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;


public class DefaultStructuredMetadataModel extends AbstractMetaDataModel implements StructuredMetaDataModel{

    private List<MetaDataField> fields;

    public DefaultStructuredMetadataModel(DataType dataType)  {
        super(dataType);

    }

    public void init(MetaDataFieldFactory fieldFactory){
        fields = fieldFactory.createFields();
    }


    @Override
    public void accept(MetaDataModelVisitor modelVisitor) {

    }



    @Override
    public List<MetaDataField> getFields() {
        return fields;
    }
}
