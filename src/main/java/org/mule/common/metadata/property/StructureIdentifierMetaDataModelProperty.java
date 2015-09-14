package org.mule.common.metadata.property;


import org.mule.common.metadata.ConnectorMetaDataEnabled;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.OperationMetaDataEnabled;

/**
 * Property used to reference a key {@link #metaDataKey} from a concrete entity, describing also if the current entity
 * is a derived one or not (see {@link #isDerivedStructure()}) and if the current {@link #metaDataKey} should be
 * displayed in Studio or not.
 */
public class StructureIdentifierMetaDataModelProperty implements MetaDataModelProperty {
    MetaDataKey metaDataKey;
    boolean derivedStructure;
    boolean generatedStructure;

    /**
     * Use {@link #StructureIdentifierMetaDataModelProperty(MetaDataKey, boolean, boolean)} instead
     */
    @Deprecated
    public StructureIdentifierMetaDataModelProperty(MetaDataKey metaDataKey, boolean derivedStructure) {
        this(metaDataKey, derivedStructure, false);
    }

    public StructureIdentifierMetaDataModelProperty(MetaDataKey metaDataKey, boolean derivedStructure, boolean generatedStructure) {
        this.metaDataKey = metaDataKey;
        this.derivedStructure = derivedStructure;
        this.generatedStructure = generatedStructure;
    }

    public MetaDataKey getMetaDataKey() {
        return metaDataKey;
    }

    /**
     * @return False if the {@link MetaDataModel} that contains this property is describing an
     * entity from the result of the list {@link ConnectorMetaDataEnabled#getMetaDataKeys()}.
     * True otherwise.
     */
    public boolean isDerivedStructure() {
        return derivedStructure;
    }

    /**
     * @return False if the {@link #metaDataKey} that describes the {@link MetaDataModel} which contains this property
     * was used for both operations: {@link OperationMetaDataEnabled#getOutputMetaData(MetaData)} and
     * {@link OperationMetaDataEnabled#getInputMetaData()}.
     * True otherwise.
     */
    public boolean isGeneratedStructure() {
        return generatedStructure;
    }
}
