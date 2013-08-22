package org.mule.common.metadata.field.property;

/**
 * Property which holds the Java class's name of an specified field
 */
public class ImplementationClassMetaDataFieldProperty implements MetaDataFieldProperty {
    private String name;

    public ImplementationClassMetaDataFieldProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
