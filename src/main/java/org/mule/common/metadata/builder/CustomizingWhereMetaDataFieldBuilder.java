package org.mule.common.metadata.builder;


public interface CustomizingWhereMetaDataFieldBuilder<P extends MetaDataBuilder<?>> extends PropertyCustomizableMetaDataBuilder<P> {
	
	public AddingOperatorsMetaDataFieldBuilder<P> withSpecificOperations();
	
	public PropertyCustomizableMetaDataBuilder<P> withDefaultOperations();
}
