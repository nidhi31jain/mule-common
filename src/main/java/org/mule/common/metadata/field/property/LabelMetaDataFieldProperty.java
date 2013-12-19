package org.mule.common.metadata.field.property;

/**
 * Property used to specify a label of a field that will be something more descriptive than the name.
 */
public class LabelMetaDataFieldProperty implements MetaDataFieldProperty {

    private String label;

    public LabelMetaDataFieldProperty(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
