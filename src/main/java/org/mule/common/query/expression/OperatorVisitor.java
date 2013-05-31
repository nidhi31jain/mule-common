package org.mule.common.query.expression;

/**
 * A visitor for translating operators on each query language
 */
public interface OperatorVisitor {
    String lessOperator();

    String greaterOperator();

    String lessOrEqualsOperator();

    String equalsOperator();

    String notEqualsOperator();

    String greaterOrEqualsOperator();
}
