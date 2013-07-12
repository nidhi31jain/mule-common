/**
 *
 */
package org.mule.common.query.expression;

public class LikeOperator    implements BinaryOperator
{

    @Override
    public String accept(OperatorVisitor operatorVisitor)
    {
        return operatorVisitor.likeOperator();
    }
}
