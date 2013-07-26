package org.mule.common.metadata;

import org.mule.common.query.expression.Operator;

import java.util.List;

/**
 */
public interface FieldMetaDataModel {
    Boolean isSelectCapable();

    Boolean isWhereCapable();

    Boolean isSortCapable();

    List<Operator> getSupportedOperators();
}
