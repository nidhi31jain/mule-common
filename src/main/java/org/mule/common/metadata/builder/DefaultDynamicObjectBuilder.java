package org.mule.common.metadata.builder;

import java.util.ArrayList;
import java.util.List;

import org.mule.common.metadata.DefaultDefinedMapMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.datatype.DataType;

public class DefaultDynamicObjectBuilder<P extends MetaDataBuilder<?>> implements EnumMetaDataBuilder<P> {
    private String name;
    private List<DefaultMetaDataFieldBuilder> fields;
    private P parentBuilder;

    DefaultDynamicObjectBuilder(String name,P parentBuilder) {
        this.name = name;
        this.parentBuilder = parentBuilder;
        this.fields = new ArrayList<DefaultMetaDataFieldBuilder>();
    }

    @Override
    public PropertyCustomizableMetaDataBuilder<P> addSimpleField(String name, DataType dataType) {
        fields.add(new DefaultMetaDataFieldBuilder(name, new DefaultSimpleMetaDataBuilder(dataType)));
        return this;
    }

    @Override
    public PropertyCustomizableMetaDataBuilder<P> addSimpleField(String name, DataType dataType, String implClass) {
    	DefaultSimpleMetaDataBuilder builder = new DefaultSimpleMetaDataBuilder(dataType);
    	builder.setImplClass(implClass);
		fields.add(new DefaultMetaDataFieldBuilder(name, builder));
    	return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public DynamicObjectFieldBuilder<P> addPojoField(String name, Class<?> pojo) {
        fields.add(new DefaultMetaDataFieldBuilder(name, new DefaultPojoMetaDataBuilder(pojo, this)));
        return this;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addListOfDynamicObjectField(String name) {
        DefaultListMetaDataBuilder<?> builder = new DefaultListMetaDataBuilder<DynamicObjectFieldBuilder<?>>(this);
        DynamicObjectBuilder<?> dynamicObjectBuilder = builder.ofDynamicObject(name);
        fields.add(new DefaultMetaDataFieldBuilder(name, builder));
        return (DynamicObjectFieldBuilder) dynamicObjectBuilder;
    }

    @SuppressWarnings("unchecked")
	@Override
    public DynamicObjectFieldBuilder<DynamicObjectFieldBuilder<P>> addDynamicObjectField(String name) {
    	@SuppressWarnings("rawtypes")
		DefaultDynamicObjectBuilder builder = new DefaultDynamicObjectBuilder<DefaultDynamicObjectBuilder<?>>(name,this);
        fields.add(new DefaultMetaDataFieldBuilder(name, builder));
        return builder;
    }

    @Override
    public P endDynamicObject() {
        return parentBuilder;
    }

    @Override
    public DefinedMapMetaDataModel build() {
        List<MetaDataField> fieldList = new ArrayList<MetaDataField>();
        for (DefaultMetaDataFieldBuilder field : fields)
        {
            fieldList.add(field.build());
        }
        return new DefaultDefinedMapMetaDataModel(fieldList, name);
    }


    private DefaultMetaDataFieldBuilder getCurrentField() {
        return fields.get(fields.size() - 1);
    }

    @Override
    public DynamicObjectFieldBuilder<P> withAccessType(MetaDataField.FieldAccessType accessType) {
        getCurrentField().withAccessType(accessType);
        return this;
    }

	@Override
	public PropertyCustomizableMetaDataBuilder<P> isSelectCapable(boolean capable) {
		getCurrentField().isSelectCapable(capable);
		return this;
	}

	@Override
	public PropertyCustomizableMetaDataBuilder<P> isOrderByCapable(boolean capable) {
		getCurrentField().isOrderByCapable(capable);
		return this;
	}

	@Override
	public CustomizingWhereMetaDataFieldBuilder<P> isWhereCapable(boolean capable) {
		getCurrentField().isWhereCapable(capable);
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> withSpecificOperations() {
		return this;
	}

	@Override
	public PropertyCustomizableMetaDataBuilder<P> withDefaultOperations() {
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsEquals() {
		getCurrentField().supportsEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsNotEquals() {
		getCurrentField().supportsNotEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsGreater() {
		getCurrentField().supportsGreater();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsGreaterOrEquals() {
		getCurrentField().supportsGreaterOrEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsLess() {
		getCurrentField().supportsLess();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsLessOrEquals() {
		getCurrentField().supportsLessOrEquals();
		return this;
	}

	@Override
	public AddingOperatorsMetaDataFieldBuilder<P> supportsLike() {
		getCurrentField().supportsLike();
		return this;
	}

	@Override
	public EnumMetaDataBuilder<P> setValues(String... strings) {
		getCurrentField().setEnumValues(strings);
		return this;
	}

	@Override
	public EnumMetaDataBuilder<P> addEnumField(String name) {
        fields.add(new DefaultMetaDataFieldBuilder(name, new DefaultSimpleMetaDataBuilder(DataType.ENUM)));
        return this;
	}

	@Override
	public PropertyCustomizableMetaDataBuilder<P> setExample(String example) {
		getCurrentField().setExample(example);
		return this;
	}
}
