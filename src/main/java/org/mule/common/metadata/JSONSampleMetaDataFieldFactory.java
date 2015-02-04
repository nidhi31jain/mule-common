package org.mule.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.mule.common.metadata.datatype.DataType;

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

    private void processElement(String name, JsonNode element, List<MetaDataField> metaDataFields) {
        if (element.isObject()) {
            JSONSampleMetaDataFieldFactory fieldFactory = new JSONSampleMetaDataFieldFactory((ObjectNode) element);
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory)));
        } else if (element.isArray()) {
            JsonNode child = JSONMetaDataHelper.getFirstChild((ArrayNode) element);
            // If the array is empty we assume String type
            DataType dataType = child == null ? DataType.STRING : !child.isObject() ? JSONMetaDataHelper.getType(child) : DataType.JSON;
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(dataType == DataType.JSON ?  new DefaultStructuredMetadataModel(DataType.JSON, new JSONSampleMetaDataFieldFactory((ObjectNode) child)) : new DefaultSimpleMetaDataModel(dataType))));
        } else {
            metaDataFields.add(new DefaultMetaDataField(name, new DefaultSimpleMetaDataModel(JSONMetaDataHelper.getType(element))));
        }
    }


}