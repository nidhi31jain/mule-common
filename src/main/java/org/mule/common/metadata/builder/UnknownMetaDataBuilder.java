package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefaultUnknownMetaDataModel;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.UnknownMetaDataModel;

/**
 *
 */
public class UnknownMetaDataBuilder<T extends MetaDataModel> implements MetaDataBuilder<UnknownMetaDataModel>
{

    @Override
    public UnknownMetaDataModel build()
    {
        return new DefaultUnknownMetaDataModel();
    }
}
