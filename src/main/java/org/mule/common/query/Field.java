package org.mule.common.query;

/**
 * Represents a field in a query
 *
 * @author Mulesoft, Inc
 */
public class Field {

    /**
     * Field name
     */
    private String name;

    /**
     * Field type
     */
    private String type;

    public Field(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public Field(String name) {
    	this(name, null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
