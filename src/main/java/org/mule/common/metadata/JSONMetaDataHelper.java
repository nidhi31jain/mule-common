package org.mule.common.metadata;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class JSONMetaDataHelper  {

    private JSONMetaDataHelper() {
    }

    public static JsonElement getFirstChild(JsonArray array) {
        return array.size() > 0 ? array.get(0) : null;
    }

    public static DataType getType(JsonPrimitive primitive) {
        if (primitive.isString()) {
            return DataType.STRING;
        } else if (primitive.isBoolean()) {
            return DataType.BOOLEAN;
        } else if (primitive.isNumber()) {
            if(isInteger(primitive.getAsString())){
                return DataType.INTEGER;
            } else {
                return DataType.DOUBLE;
            }
        }
        return DataType.STRING;
    }

    private static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}