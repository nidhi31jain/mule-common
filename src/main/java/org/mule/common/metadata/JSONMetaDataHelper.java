package org.mule.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mule.common.metadata.datatype.DataType;

public final class JSONMetaDataHelper  {

    private JSONMetaDataHelper() {
    }

    public static MetaDataModel buildModelFromNode(JsonNode node) {
        MetaDataModel result = null;
        if (node.isObject()) {
            JSONSampleMetaDataFieldFactory fieldFactory = new JSONSampleMetaDataFieldFactory((ObjectNode) node);
            result =  new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory);
        } else if (node.isArray()) {
            JsonNode child = JSONMetaDataHelper.getFirstChild((ArrayNode) node);
            if (child == null) {
                // If the array is empty we assume the data type as String
                result =  new DefaultListMetaDataModel(new DefaultSimpleMetaDataModel(DataType.STRING));
            } else {
                result =  new DefaultListMetaDataModel(buildModelFromNode(child));
            }
        } else {
            result = new DefaultSimpleMetaDataModel(JSONMetaDataHelper.getType(node));
        }
        return result;
    }

    public static DataType getType(JsonNode node) {
        if (node.isTextual()) {
            return DataType.STRING;
        } else if (node.isBoolean()) {
            return DataType.BOOLEAN;
        } else if (isInteger(node)) {
            return DataType.INTEGER;
        } else if (isDouble(node)) {
            return DataType.DOUBLE;
        } else if (node.isObject()) {
            return DataType.JSON;
        } else if (node.isArray()) {
            return DataType.LIST;
        } else if (node.isNull()) {
            return DataType.STRING;
        }
        return DataType.UNKNOWN;
    }

    private static boolean isInteger(JsonNode node) {
        return node.isInt() || node.isBigInteger() || node.isLong() || node.isNumber() || node.isShort();
    }

    private static boolean isDouble(JsonNode node) {
        return node.isDouble() || node.isBigDecimal() || node.isFloat() || node.isFloatingPointNumber();
    }

    public static JsonNode getFirstChild(ArrayNode array) {
        return array.size() > 0 ? array.get(0) : null;
    }
}