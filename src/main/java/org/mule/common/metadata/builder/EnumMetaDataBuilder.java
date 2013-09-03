package org.mule.common.metadata.builder;

public interface EnumMetaDataBuilder<P extends MetaDataBuilder<?>> extends AddingOperatorsMetaDataFieldBuilder<P> {

	EnumMetaDataBuilder<P> setValues(String...strings);

}
