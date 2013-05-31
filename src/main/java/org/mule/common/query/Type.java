package org.mule.common.query;

/**
 * Represents a type for Mule Query Builder
 *
 * @author Mulesoft, Inc
 */
public class Type {

    public Type(String typeName){
        this.name = typeName;
    }

    /**
     * Type's name
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
