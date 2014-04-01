package org.mule.common.metadata.key.property;

/**
 * As {@link org.mule.common.metadata.DefaultMetaDataKey} will have a field for specifying the category, this class
 * should not be used any longer.
 */
@Deprecated
public class CategoryKeyProperty implements MetaDataKeyProperty {
    private String name;

    public CategoryKeyProperty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryKeyProperty)) return false;

        CategoryKeyProperty that = (CategoryKeyProperty) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
