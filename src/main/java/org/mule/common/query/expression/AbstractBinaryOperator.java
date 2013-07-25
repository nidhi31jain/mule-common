package org.mule.common.query.expression;

import org.mule.common.query.DefaultOperatorVisitor;

/**
 */
public abstract class AbstractBinaryOperator implements BinaryOperator {

    public String toString() {
        return accept(new DefaultOperatorVisitor());
    }
}
