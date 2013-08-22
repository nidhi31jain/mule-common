/**
 *
 */
package org.mule.common.metadata.builder;


import org.mule.common.metadata.DefaultDefinedMapMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.DsqlMetaDataFieldProperty;

import java.util.ArrayList;
import java.util.List;

public class DefaultDynamicObjectBuilder<P extends MetaDataBuilder<?>> implements DynamicObjectFieldBuilder<P>
{


    private String name;
    private List<MetaDataFieldBuilder> fields;
    private P parentBuilder;


    DefaultDynamicObjectBuilder(String name,P parentBuilder)
    {
        this.name = name;
        this.parentBuilder = parentBuilder;
        this.fields = new ArrayList<MetaDataFieldBuilder>();
    }

    @Override
    public DynamicObjectFieldBuilder<P> addSimpleField(String name, DataType dataType)
    {
        fields.add(new MetaDataFieldBuilder(name, new DefaultSimpleMetaDataBuilder(dataType)));
        return this;
    }

    @Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addListOfDynamicObjectField(String name)
    {
        DefaultListMetaDataBuilder builder = new DefaultListMetaDataBuilder(this);
        DynamicObjectBuilder dynamicObjectBuilder = builder.ofDynamicObject(name);
        fields.add(new MetaDataFieldBuilder(name, builder));
        return (DynamicObjectFieldBuilder) dynamicObjectBuilder;
    }

    @Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addDynamicObjectField(String name)
    {
        DefaultDynamicObjectBuilder builder = new DefaultDynamicObjectBuilder<DefaultDynamicObjectBuilder<?>>(name,this);
        fields.add(new MetaDataFieldBuilder(name, builder));
        return builder;
    }

    @Override
    public P endDynamicObject()
    {
        return parentBuilder;
    }

    @Override
    public DefinedMapMetaDataModel build()
    {
        List<MetaDataField> fieldList = new ArrayList<MetaDataField>();
        for (MetaDataFieldBuilder field : fields)
        {
            fieldList.add(field.build());
        }
        return new DefaultDefinedMapMetaDataModel(fieldList, name);
    }

    @Override
    public DynamicObjectFieldBuilder<P> withDsqlProperty(DsqlMetaDataFieldProperty... properties)
    {
        getCurrentField().withProperty(properties);
        return this;
    }

    private MetaDataFieldBuilder getCurrentField()
    {
        return fields.get(fields.size() - 1);
    }

    @Override
    public DynamicObjectFieldBuilder<P> withAccessType(MetaDataField.FieldAccessType accessType)
    {
        getCurrentField().withAccessType(accessType);
        return this;
    }

    @Override
    public DynamicObjectFieldBuilder<P> withImplClass(String className)
    {
        getCurrentField().withImplClass(className);
        return this;
    }


}
