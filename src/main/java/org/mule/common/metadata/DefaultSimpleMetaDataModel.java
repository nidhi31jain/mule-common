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
import org.mule.common.query.expression.Operator;

import java.util.List;

public class DefaultSimpleMetaDataModel 
	extends AbstractMetaDataModel implements SimpleMetaDataModel, FieldMetaDataModel
{
    private boolean isSelectCapable = true;

    private boolean isWhereCapable = true;

    private boolean isSortCapable = true;

    private List<Operator> supportedOperators;

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
        this.isSelectCapable = true;
        this.isSortCapable = true;
        this.setSupportedOperators(dataType);
    }

    public DefaultSimpleMetaDataModel(DataType dataType, boolean isSelectCapable, boolean isSortCapable, List<Operator> supportedOperators )
    {
        this(dataType);
        this.isSelectCapable = isSelectCapable;
        this.isSortCapable = isSortCapable;
        this.setSupportedOperators(supportedOperators);
    }

    public DefaultSimpleMetaDataModel(DataType dataType, boolean isSelectCapable, boolean isSortCapable )
    {
        this(dataType);
        this.isSelectCapable=isSelectCapable;
        this.isSortCapable=isSortCapable;
    }

    public DefaultSimpleMetaDataModel(DataType dataType, List<Operator> supportedOperators )
    {
        this(dataType);
        this.setSupportedOperators(supportedOperators);
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

    private void setSupportedOperators(DataType dataType){
        this.setSupportedOperators(SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(dataType));
    }

    private void setSupportedOperators(List<Operator> supportedOperators){
        this.supportedOperators= supportedOperators;
        this.calculateWhereCapable();
    }

    /**
     * A field is 'where' capable if its operations aren't empty, otherwise there won't be able to operate
     */
    private void calculateWhereCapable() {
        this.isWhereCapable= ! this.getSupportedOperators().isEmpty();
    }

    @Override
    public boolean isSelectCapable() {
        return this.isSelectCapable;
    }

    @Override
    public boolean isWhereCapable() {
        return this.isWhereCapable;
    }

    @Override
    public boolean isSortCapable() {
        return this.isSortCapable;
    }

    @Override
    public List<Operator> getSupportedOperators() {
        return this.supportedOperators;
    }
}


