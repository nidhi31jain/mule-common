package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;

/**
 * <p>This represents a model for metadata. Could be either Pojo, List, Xml or even some Dynamic Object whose fields can variate over time.</p>
 * <p>It is <strong>highly recommended</strong> to use the {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} to build the model you want to use for describe your entities/types</p>
 */
public interface MetaDataModel
{

    DataType getDataType();

    /**
     * Used internally to determine the type of the model.
     */
    <T extends MetaDataModel> T as(Class<T> clazz);

    /**
     * Used internally for go over the model structure.
     */
    void accept(MetaDataModelVisitor modelVisitor);

    /**
     * Return an implementation class if any.
     */
    String getImplementationClass();

    /**
     * Properties used for advanced scenarios.
     */
    List<MetaDataModelProperty> getProperties();

    boolean addProperty(MetaDataModelProperty metaDataModelProperty);

    boolean removeProperty(MetaDataModelProperty metaDataModelProperty);

    boolean hasProperty(Class<? extends MetaDataModelProperty> metaDataModelProperty);

    /**
     * Property used for advanced scenarios.
     */
    <T extends MetaDataModelProperty> T getProperty(Class<T> metaDataModelProperty);
}
