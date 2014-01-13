package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

/**
 *
 */
public class DefaultUnknownMetaDataModel extends AbstractMetaDataModel implements UnknownMetaDataModel
{


    public DefaultUnknownMetaDataModel()
    {
        super(DataType.UNKNOWN);
    }


    @Override
    public void accept(MetaDataModelVisitor modelVisitor)
    {
        modelVisitor.visitUnknownMetaDataModel(this);
    }
}
