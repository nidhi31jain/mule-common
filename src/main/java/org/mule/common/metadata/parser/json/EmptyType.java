package org.mule.common.metadata.parser.json;


public class EmptyType implements JSONType{

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public String explain(Object obj) {
        return null;
    }

    @Override
    public boolean isOptional() {
        return false;
    }

    @Override
    public boolean isJSONPrimitive() {
        return false;
    }

    @Override
    public boolean isJSONArray() {
        return false;
    }

    @Override
    public boolean isJSONObject() {
        return false;
    }

}
