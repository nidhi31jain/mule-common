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
        typeMapping.put(JSONType.Boolean.class, DataType.BOOLEAN);
        typeMapping.put(JSONType.Double.class, DataType.DOUBLE);
        typeMapping.put(JSONType.Empty.class, DataType.VOID);
        typeMapping.put(JSONType.Integer.class, DataType.INTEGER);
        typeMapping.put(JSONType.String.class, DataType.STRING);
        typeMapping.put(JSONType.Number.class, DataType.NUMBER);
    }

    public static DataType getDataType(JSONType jsonType) {

        DataType dataType = typeMapping.get(jsonType.getClass());
        if (dataType != null) {
            return dataType;
        }
        return DataType.STRING;
    }

}
