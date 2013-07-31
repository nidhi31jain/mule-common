package org.mule.common.metadata;

import org.mule.common.query.expression.Operator;

import java.util.List;

/**
 */
public interface FieldMetaDataModel {
    boolean isSelectCapable();

    boolean isWhereCapable();

    boolean isSortCapable();

    List<Operator> getSupportedOperators();
}
