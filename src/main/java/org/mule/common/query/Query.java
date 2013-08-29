package org.mule.common.query;

@Deprecated
public interface Query {

   public void accept(QueryVisitor queryVisitor);
}
