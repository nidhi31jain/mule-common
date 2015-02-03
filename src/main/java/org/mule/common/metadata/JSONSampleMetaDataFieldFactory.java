package org.mule.common.metadata;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.mule.common.metadata.datatype.DataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSONSampleMetaDataFieldFactory implements MetaDataFieldFactory {

    private JsonObject jsonObject;

    public JSONSampleMetaDataFieldFactory(JsonObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public List<MetaDataField> createFields() {
        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();

        processObject(jsonObject, metaDataFields);

        return metaDataFields;
    }

    private void processObject(JsonObject object, List<MetaDataField> metaDataFields) {
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            processElement(entry.getKey(), entry.getValue(), metaDataFields);
        }
    }

    private void processElement(String name, JsonElement element, List<MetaDataField> metaDataFields) {
        if (element.isJsonObject()) {
            JSONSampleMetaDataFieldFactory fieldFactory = new JSONSampleMetaDataFieldFactory((JsonObject) element);
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory)));
        } else if (element.isJsonArray()) {
            JsonElement child = JSONMetaDataHelper.getFirstChild((JsonArray) element);
            // If the array is empty we assume String type
            DataType dataType = child == null ? DataType.STRING : child.isJsonPrimitive() ? JSONMetaDataHelper.getType((JsonPrimitive) child) : DataType.JSON;
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(dataType == DataType.JSON ?  new DefaultStructuredMetadataModel(DataType.JSON, new JSONSampleMetaDataFieldFactory((JsonObject) child)) : new DefaultSimpleMetaDataModel(dataType))));
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = (JsonPrimitive) element;
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultSimpleMetaDataModel(JSONMetaDataHelper.getType(primitive))));
        }
    }


}