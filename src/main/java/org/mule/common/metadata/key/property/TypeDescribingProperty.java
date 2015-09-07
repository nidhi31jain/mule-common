package org.mule.common.metadata.key.property;

/**
 * Property used between DevKit and the connector. This property will help the connector's developer to discriminate
 * between input or output metadata.
 * This object is deprecated, use {@link org.mule.common.metadata.property.CatalogTypeProperty} instead
 */
@Deprecated
public class TypeDescribingProperty implements MetaDataKeyProperty {

    private TypeScope typeScope;
    private String method;

    public TypeDescribingProperty(TypeScope typeScope, String method) {
        this.typeScope = typeScope;
        this.method = method;
    }

    public TypeScope getTypeScope() {
        return typeScope;
    }

    public String getMethod() {
        return method;
    }

    public enum TypeScope{
        INPUT, OUTPUT;
    }
}