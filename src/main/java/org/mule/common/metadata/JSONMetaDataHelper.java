package org.mule.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.mule.common.metadata.datatype.DataType;

public final class JSONMetaDataHelper  {

    private JSONMetaDataHelper() {
    }

    public static JsonNode getFirstChild(ArrayNode array) {
        return array.size() > 0 ? array.get(0) : null;
    }

    public static DataType getType(JsonNode node) {
        if (node.isTextual()) {
            return DataType.STRING;
        } else if (node.isBoolean()) {
            return DataType.BOOLEAN;
        } else if (node.isInt()) {
            return DataType.INTEGER;
        } else if (node.isDouble()) {
            return DataType.DOUBLE;
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