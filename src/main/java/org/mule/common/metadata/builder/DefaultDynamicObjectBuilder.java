/**
 *
 */
package org.mule.common.metadata.builder;


import java.util.ArrayList;
import java.util.List;

import org.mule.common.metadata.DefaultDefinedMapMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.datatype.DataType;

public class DefaultDynamicObjectBuilder<P extends MetaDataBuilder<?>> implements DynamicObjectFieldBuilder<P>, CustomizingWhereMetaDataFieldBuilder, AddingOperatorsMetaDataFieldBuilder
{

    private String name;
    private List<DefaultMetaDataFieldBuilder> fields;
    private P parentBuilder;

    DefaultDynamicObjectBuilder(String name,P parentBuilder)
    {
        this.name = name;
        this.parentBuilder = parentBuilder;
        this.fields = new ArrayList<DefaultMetaDataFieldBuilder>();
    }

    @Override
    public DynamicObjectFieldBuilder<P> addSimpleField(String name, DataType dataType)
    {
        fields.add(new DefaultMetaDataFieldBuilder(name, new DefaultSimpleMetaDataBuilder(dataType)));
        return this;
    }

    @Override
    public DynamicObjectFieldBuilder<P> addSimpleField(String name, DataType dataType, String implClass)
    {
    	DefaultSimpleMetaDataBuilder builder = new DefaultSimpleMetaDataBuilder(dataType);
    	builder.setImplClass(implClass);
		fields.add(new DefaultMetaDataFieldBuilder(name, builder));
    	return this;
    }

    @Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addListOfDynamicObjectField(String name)
    {
        DefaultListMetaDataBuilder builder = new DefaultListMetaDataBuilder(this);
        DynamicObjectBuilder dynamicObjectBuilder = builder.ofDynamicObject(name);
        fields.add(new DefaultMetaDataFieldBuilder(name, builder));
        return (DynamicObjectFieldBuilder) dynamicObjectBuilder;
    }

    @Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addDynamicObjectField(String name)
    {
        DefaultDynamicObjectBuilder builder = new DefaultDynamicObjectBuilder<DefaultDynamicObjectBuilder<?>>(name,this);
        fields.add(new DefaultMetaDataFieldBuilder(name, builder));
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
        for (DefaultMetaDataFieldBuilder field : fields)
        {
            fieldList.add(field.build());
        }
        return new DefaultDefinedMapMetaDataModel(fieldList, name);
    }


    private DefaultMetaDataFieldBuilder getCurrentField()
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
	public DynamicObjectFieldBuilder isSelectCapable(boolean capable) 
	{
		getCurrentField().isSelectCapable(capable);
		return this;
	}

	@Override
	public DynamicObjectFieldBuilder isOrderByCapable(boolean capable) 
	{
		getCurrentField().isOrderByCapable(capable);
		return this;
	}

	@Override
	public CustomizingWhereMetaDataFieldBuilder isWhereCapable(boolean capable) 
	{
		getCurrentField().isWhereCapable(capable);
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder withSpecificOperations() 
	{
		return this;
	}

	@Override
	public DynamicObjectFieldBuilder withDefaultOperations() 
	{
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsEquals() 
	{
		getCurrentField().supportsEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsNotEquals() 
	{
		getCurrentField().supportsNotEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsGreater() 
	{
		getCurrentField().supportsGreater();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsGreaterOrEquals() 
	{
		getCurrentField().supportsGreaterOrEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsLess() 
	{
		getCurrentField().supportsLess();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsLessOrEquals() 
	{
		getCurrentField().supportsLessOrEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder supportsLike() 
	{
		getCurrentField().supportsLike();
		return this;
	}


}
