package org.mule.common.metadata.property;


import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModelProperty;

/**
 * Property used to reference a key {@link #metaDataKey} from a concrete entity, describing also if the current entity
 * is a derived one or not (see {@link #isDerivedStructure()})
 */
public class StructureIdentifierMetaDataModelProperty implements MetaDataModelProperty {
    MetaDataKey metaDataKey;
    boolean derivedStructure;

    public StructureIdentifierMetaDataModelProperty(MetaDataKey metaDataKey, boolean derivedStructure) {
        this.metaDataKey = metaDataKey;
        this.derivedStructure = derivedStructure;
    }

    public MetaDataKey getMetaDataKey() {
        return metaDataKey;
    }

    /**
     * @return False if the {@link org.mule.common.metadata.MetaDataModel} that contains this property is describing an
     * entity from the result of the list {@link org.mule.common.metadata.ConnectorMetaDataEnabled#getMetaDataKeys()}.
     * True otherwise.
     */
    public boolean isDerivedStructure() {
        return derivedStructure;
    }
}
