package org.mule.common.metadata.parser.json;

public class OptionalType implements JSONType
{

    private JSONType innerType;

    public OptionalType(JSONType t)
    {
        innerType = t;
    }

    public boolean contains(Object obj)
    {
        return org.json.JSONObject.NULL.equals(obj) || innerType.contains(obj);
    }

    public java.lang.String explain(Object obj)
    {
        return innerType.explain(obj);
    }

    public boolean isOptional()
    {
        return true;
    }

    @Override
    public boolean isJSONPrimitive()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isJSONArray()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isJSONObject()
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isJSONPointer()
    {
        return false;
    }
}
