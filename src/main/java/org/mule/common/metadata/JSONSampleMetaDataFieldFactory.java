package org.mule.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JSONSampleMetaDataFieldFactory implements MetaDataFieldFactory {

    private ObjectNode jsonObject;

    public JSONSampleMetaDataFieldFactory(ObjectNode jsonObject) {
        this.jsonObject = jsonObject;
    }

    @Override
    public List<MetaDataField> createFields() {
        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();

        processObject(jsonObject, metaDataFields);

        return metaDataFields;
    }

    private void processObject(ObjectNode object, List<MetaDataField> metaDataFields) {
        Iterator<Map.Entry<String, JsonNode>> fields = object.fields();

        while(fields.hasNext()) {
            Map.Entry<String, JsonNode> entry = fields.next();
            processElement(entry.getKey(), entry.getValue(), metaDataFields);
        }
    }

    private static void processElement(String name, JsonNode element, List<MetaDataField> metaDataFields) {
        metaDataFields.add(new DefaultMetaDataField(name, JSONMetaDataHelper.buildModelFromNode(element)));
    }
}