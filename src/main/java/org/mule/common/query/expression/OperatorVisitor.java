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

    String likeOperator();

    /**
     *  This method simply acts a friendly reminder not to implement OperatorVisitor directly and instead extend DefaultOperatorVisitor.
     */
    void _dont_implement_OperatorVisitor___instead_extend_DefaultOperatorVisitor();
}
