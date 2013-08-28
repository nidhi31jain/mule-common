/**
 *
 */
package org.mule.common.metadata.builder;


import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.datatype.DataType;

public interface DynamicObjectBuilder<P extends MetaDataBuilder<?>> extends MetaDataBuilder<DefinedMapMetaDataModel>
{

    DynamicObjectFieldBuilder<P> addSimpleField(String name, DataType dataType);
    
    DynamicObjectFieldBuilder<P> addSimpleField(String name, DataType dataType, String implClass);

    DynamicObjectFieldBuilder<P> addPojoField(String name, Class<?> pojo);

    DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addListOfDynamicObjectField(String name);

    DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addDynamicObjectField(String name);

    P endDynamicObject();

}
