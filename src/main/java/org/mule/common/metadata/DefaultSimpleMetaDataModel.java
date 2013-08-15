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
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.LikeOperator;
import org.mule.common.query.expression.NotEqualsOperator;
import org.mule.common.query.expression.Operator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public String getDefaultImplementationClass() {
        switch (this.getDataType()) {
            case BOOLEAN:
                return "java.lang.Boolean";
            case ENUM:
                return "java.lang.Enum";
            case DATE:
                return "java.util.Date";
            case DATE_TIME:
                return "java.util.Calendar";
            case BYTE:
                return "java.lang.Byte";
            case NUMBER:
                return "java.lang.Number";
            case STRING:
                return "java.lang.String";
            case VOID:
                return "java.lang.Void";
            case STREAM:
                return "java.io.InputStream";
        }
        return null;
    }
}



