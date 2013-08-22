/**
 *
 */
package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public class DefaultSimpleMetaDataBuilder implements SimpleMetaDataBuilder
{

    private DataType dataType;

    DefaultSimpleMetaDataBuilder(DataType dataType)
    {
        this.dataType = dataType;
    }

    @Override
    public MetaDataModel build()
    {
        return new DefaultSimpleMetaDataModel(dataType);
    }
}
