package org.mule.common.metadata.field.property;

/**
 * Property used to specify a description of a field.
 */
public class DescriptionMetaDataFieldProperty implements MetaDataFieldProperty {

    private String description;

    public DescriptionMetaDataFieldProperty(String label) {
        this.description = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
