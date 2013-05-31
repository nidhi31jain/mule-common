package org.mule.common.query.expression;

import org.mule.common.query.Field;

/**
 * Represents a binary operator for a field
 */

public interface BinaryOperator {

    String accept(OperatorVisitor operatorVisitor);
}
