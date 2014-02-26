package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

/**
 * <p>Shouldn't use this directly. Use {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} instead.</p>
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
