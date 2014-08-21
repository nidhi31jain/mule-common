package org.mule.common.metadata.parser.json;

import org.mule.common.metadata.datatype.DataType;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by studio on 23/07/2014.
 */
public class JSONTypeUtils {

    private static final Map<Class<?>, DataType> typeMapping = new HashMap<Class<?>, DataType>();
    static {
        typeMapping.put(JSONType.Everything.class, DataType.UNKNOWN);
        typeMapping.put(JSONType.BooleanType.class, DataType.BOOLEAN);
        typeMapping.put(JSONType.DoubleType.class, DataType.DOUBLE);
        typeMapping.put(JSONType.Empty.class, DataType.VOID);
        typeMapping.put(JSONType.IntegerType.class, DataType.INTEGER);
        typeMapping.put(JSONType.StringType.class, DataType.STRING);
        typeMapping.put(JSONType.NumberType.class, DataType.NUMBER);
    }

    public static DataType getDataType(JSONType jsonType) {

        DataType dataType = typeMapping.get(jsonType.getClass());
        if (dataType != null) {
            return dataType;
        }
        return DataType.STRING;
    }

}
