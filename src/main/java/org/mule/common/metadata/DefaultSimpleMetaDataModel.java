/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;


import java.util.HashSet;
import java.util.Set;

/**
 * <p>Simple type metadata representation</p>
 * <p>Shouldn't use this directly. Use {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} instead.</p>
 */
public class DefaultSimpleMetaDataModel 
	extends AbstractMetaDataModel implements SimpleMetaDataModel
{

    public static final Set<DataType> complexTypes;

    static {
        complexTypes = new HashSet<DataType>();

        complexTypes.add(DataType.POJO);
        complexTypes.add(DataType.MAP);
        complexTypes.add(DataType.UNKNOWN);
        complexTypes.add(DataType.LIST);
        complexTypes.add(DataType.XML);
        complexTypes.add(DataType.CSV);
        complexTypes.add(DataType.JSON);
    }

    /**
     * Used for Define Simple Types
     * @param dataType
     */
    public DefaultSimpleMetaDataModel(DataType dataType)
    {
        super(dataType);

        if (complexTypes.contains(dataType))
        {
            throw new IllegalArgumentException("Invalid DataType for SimpleMetadataModel " + dataType);
        }
    }

    @Override
    public String toString()
    {
        return "DefaultSimpleMetaDataModel:{ dataType:" + getDataType() != null ? getDataType().toString() : "null" + " }";
    }

    @Override
    public void accept(MetaDataModelVisitor modelVisitor) {
        modelVisitor.visitSimpleMetaDataModel(this);
    }

}



