package org.mule.common.metadata.key.property;

public class CategoryKeyProperty implements MetaDataKeyProperty {
    private String name;

    public CategoryKeyProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
