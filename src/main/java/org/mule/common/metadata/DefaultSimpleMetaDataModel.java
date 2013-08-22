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
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.metadata.exception.NoImplementationClassException;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.LikeOperator;
import org.mule.common.query.expression.NotEqualsOperator;
import org.mule.common.query.expression.Operator;

import java.io.InputStream;
import java.util.*;

public class DefaultSimpleMetaDataModel 
	extends AbstractMetaDataModel implements SimpleMetaDataModel
{
    /**
     * Used for Define Simple Types
     * @param dataType
     */
    public DefaultSimpleMetaDataModel(DataType dataType)
    {
        super(dataType);
        if (dataType == DataType.POJO || dataType == DataType.MAP || dataType == DataType.LIST || dataType == DataType.XML || dataType == DataType.CSV || dataType == DataType.JSON)
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

    @Override
    public String getDefaultImplementationClass() throws NoImplementationClassException{
        switch (this.getDataType()) {
            case BOOLEAN:
                return Boolean.class.getName();
            case ENUM:
                return Enum.class.getName();
            case DATE:
                return Date.class.getName();
            case DATE_TIME:
                return Calendar.class.getName();
            case BYTE:
                return Byte.class.getName();
            case NUMBER:
                return Number.class.getName();
            case STRING:
                return String.class.getName();
            case VOID:
                return Void.class.getName();
            case STREAM:
                return InputStream.class.getName();
            default:
                throw new NoImplementationClassException("There is no default implementation class for the DataType " + this.getDataType().toString());
        }
    }
}



