package org.mule.common.metadata.datatype;

public enum DataType
{
    VOID, //TODO to be removed
    BOOLEAN,
    NUMBER,
    STRING,
    BYTE,
    STREAM, //TODO to be removed
    ENUM,
    DATE,
    DATE_TIME,
    POJO,
    LIST,
    MAP,
    XML,
    CSV,
    JSON
    // TODO: how do we model a UNION type (e.j. Object or Exception)
}
