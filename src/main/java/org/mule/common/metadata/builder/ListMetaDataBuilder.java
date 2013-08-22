/**
 *
 */
package org.mule.common.metadata.builder;

import org.mule.common.metadata.ListMetaDataModel;

public interface ListMetaDataBuilder<P extends MetaDataBuilder> extends MetaDataBuilder<ListMetaDataModel>
{

    PojoMetaDataBuilder<ListMetaDataBuilder<P>> ofPojo(Class<?> pojoClass);

    DynamicObjectBuilder<ListMetaDataBuilder<P>> ofDynamicObject(String name);

    ListMetaDataBuilder<ListMetaDataBuilder<P>> ofList();

    P endList();
}
