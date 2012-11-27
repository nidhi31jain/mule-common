package org.mule.common.metadata.datatype;

public enum DataType
{
	BOOLEAN,
	NUMBER,
	STRING,
	BYTE_ARRAY,
	STREAM,
	ENUM, 
	DATE_TIME,
	POJO,
	LIST,
	MAP
	// TODO: how do we model a UNION type (e.j. Object or Exception)
}
