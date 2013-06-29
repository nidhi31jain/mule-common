package org.mule.common.metadata.datatype;

public enum DataType
{
    VOID,
    BOOLEAN,
    NUMBER,
    STRING,
    BYTE,
    STREAM,
    ENUM,
    DATE_TIME,
    POJO,
    LIST,
    MAP,
    XML,
    CSV,
    JSON
    // TODO: how do we model a UNION type (e.j. Object or Exception)
}
