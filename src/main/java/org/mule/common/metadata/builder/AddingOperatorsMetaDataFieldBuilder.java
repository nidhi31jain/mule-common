package org.mule.common.metadata.builder;

public interface AddingOperatorsMetaDataFieldBuilder extends CustomizingWhereMetaDataFieldBuilder {

	AddingOperatorsMetaDataFieldBuilder supportsEquals();

	AddingOperatorsMetaDataFieldBuilder supportsNotEquals();

	AddingOperatorsMetaDataFieldBuilder supportsGreater();

	AddingOperatorsMetaDataFieldBuilder supportsGreaterOrEquals();

	AddingOperatorsMetaDataFieldBuilder supportsLess();

	AddingOperatorsMetaDataFieldBuilder supportsLessOrEquals();

	AddingOperatorsMetaDataFieldBuilder supportsLike();
}
