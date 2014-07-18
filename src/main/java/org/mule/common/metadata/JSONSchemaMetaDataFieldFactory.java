package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

import org.json.JSONObject;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.JSONArrayType;
import org.mule.common.metadata.parser.json.JSONObjectType;
import org.mule.common.metadata.parser.json.JSONType;
import org.mule.common.metadata.parser.json.SchemaEnv;

/**
 * Created by studio on 18/07/2014.
 */
public class JSONSchemaMetaDataFieldFactory implements MetaDataFieldFactory {

    private JSONType jsonSchemaType;
    protected static final String OBJECT_ELEMENT_NAME = "object";
    public static final String ARRAY_ELEMENT_NAME = "array";

    public JSONSchemaMetaDataFieldFactory(String jsonSchemaString) {
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);
        SchemaEnv schemaEnv = new SchemaEnv(null, jsonSchemaObject);
        if (jsonSchemaObject.has("type") && jsonSchemaObject.get("type").toString().toLowerCase().equals("array")) {
            jsonSchemaType = new JSONArrayType(schemaEnv, jsonSchemaObject);
        } else {
            jsonSchemaType = new JSONObjectType(schemaEnv, jsonSchemaObject);
        }

    }

    public JSONSchemaMetaDataFieldFactory(JSONObjectType type) {
        jsonSchemaType = type;
    }


    public List<MetaDataField> createFields() throws Exception {
        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();
        if (jsonSchemaType.isJSONObject()) {
            loadFields((JSONObjectType) jsonSchemaType, metaDataFields);
        } else {
            processJSONSchemaElement(jsonSchemaType, ARRAY_ELEMENT_NAME, metaDataFields);
        }
        return metaDataFields;
    }

    private void processJSONSchemaElement(JSONType property, String name, List<MetaDataField> metadata) throws Exception {
        if (property.isJSONObject()) {
            processJSONSchemaObject((JSONObjectType) property, name, metadata);
        } else if (property.isJSONPrimitive()) {
            processJSONSchemaPrimitive(property, name, metadata);
        } else if (property.isJSONArray()) {
            processJSONSchemaArray((JSONArrayType) property, name, metadata);
        }
    }

    private void processJSONSchemaObject(JSONObjectType type, String name, List<MetaDataField> metadata) throws Exception {

        DefaultStructuredMetadataModel model = buildJSONMetaDataModel(type);
        metadata.add(new DefaultMetaDataField(name, model));
        // loadFields(type, metadata);

    }

    private DefaultStructuredMetadataModel buildJSONMetaDataModel(JSONObjectType type) throws Exception {

        DefaultStructuredMetadataModel model;
        model = new DefaultStructuredMetadataModel(DataType.JSON, new JSONSchemaMetaDataFieldFactory(type));
        // loadFields(type, model.getFields());

        return model;
    }

    private void loadFields(JSONObjectType type, List<MetaDataField> metadata) throws Exception {
        String[] properties = type.getProperties();
        for (String key : properties) {
            JSONType propertyType = type.getPropertyType(key);
            processJSONSchemaElement(propertyType, key, metadata);
        }
    }

    private void processJSONSchemaArray(JSONArrayType property, String name, List<MetaDataField> metadata) {
        JSONType itemsType = property.getItemsType();

        if (itemsType.isJSONPrimitive()) { // Case List<String>
            DataType dataType = getDataType(itemsType);
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(new DefaultSimpleMetaDataModel(dataType))));
        } else {
            DefaultStructuredMetadataModel model = buildJSONMetaDataModel(itemsType);
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(model)));
        }

    }

    private void processJSONSchemaPrimitive(JSONType property, String name, List<MetaDataField> metadata) {
        DataType dataType = getDataType(property);
        metadata.add(new DefaultMetaDataField(name, new DefaultSimpleMetaDataModel(dataType)));
    }

    private IMetadataType getType(JSONType type) {
        if (type.getClass().equals(JSONType.Integer.class)) {
            return SimpleMetadataFieldType.Integer;
        } else if (type.getClass().equals(JSONType.String.class)) {
            return SimpleMetadataFieldType.String;
        } else if (type.getClass().equals(JSONType.Double.class)) {
            return SimpleMetadataFieldType.Number;
        } else if (type.getClass().equals(JSONType.Boolean.class)) {
            return SimpleMetadataFieldType.Boolean;
        }
        return SimpleMetadataFieldType.String;
    }

}
