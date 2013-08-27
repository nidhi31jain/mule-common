package org.mule.common.metadata.builder;


public interface CustomizingWhereMetaDataFieldBuilder extends PropertyCustomizableMetaDataBuilder {
	
	public AddingOperatorsMetaDataFieldBuilder withSpecificOperations();
	
	public MetaDataFieldBuilder withDefaultOperations();
}
