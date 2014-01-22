package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

/**
 * Property used to specify a label of a field that will be something more descriptive than the name.
 */
public class LabelMetaDataProperty implements MetaDataFieldProperty, MetaDataModelProperty
{

    private String label;

    public LabelMetaDataProperty(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
