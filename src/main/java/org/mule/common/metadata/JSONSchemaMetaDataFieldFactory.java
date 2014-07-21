package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.util.Map;

import org.json.JSONObject;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

/**
 * Created by studio on 18/07/2014.
 */
public class JSONSchemaMetaDataFieldFactory implements MetaDataFieldFactory {

    private static final Map<JSONType, DataType> typeMapping = new HashMap<JSONType, DataType>();
    static{
        typeMapping.put(new JSONType.Everything(), DataType.UNKNOWN);
        typeMapping.put(new JSONType.Boolean(), DataType.BOOLEAN);
        typeMapping.put(new JSONType.Double(), DataType.DOUBLE);
        typeMapping.put(new JSONType.Empty(), DataType.VOID);
        typeMapping.put(new JSONType.Integer(), DataType.INTEGER);
        typeMapping.put(new JSONType.String(), DataType.STRING);
        typeMapping.put(new JSONType.Number(), DataType.NUMBER);
    }


    private JSONType jsonSchemaType;
    protected static final String OBJECT_ELEMENT_NAME = "object";
    public static final String ARRAY_ELEMENT_NAME = "array";

    public JSONSchemaMetaDataFieldFactory(String jsonSchemaString) throws SchemaException {
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);
        SchemaEnv schemaEnv = new SchemaEnv(null, jsonSchemaObject);
        if (jsonSchemaObject.has("type") && jsonSchemaObject.get("type").toString().toLowerCase().equals(ARRAY_ELEMENT_NAME)) {
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

    }

    private DefaultStructuredMetadataModel buildJSONMetaDataModel(JSONObjectType type) throws Exception {

        DefaultStructuredMetadataModel model;
        model = new DefaultStructuredMetadataModel(DataType.JSON, new JSONSchemaMetaDataFieldFactory(type));

        return model;
    }

    private void loadFields(JSONObjectType type, List<MetaDataField> metadata) throws Exception {
        String[] properties = type.getProperties();
        for (String key : properties) {
            JSONType propertyType = type.getPropertyType(key);
            processJSONSchemaElement(propertyType, key, metadata);
        }
    }

    private void processJSONSchemaArray(JSONArrayType property, String name, List<MetaDataField> metadata) throws Exception {
        JSONType itemsType = property.getItemsType();

        if (itemsType.isJSONPrimitive()) { // Case List<String>
            DataType dataType = getDataType(itemsType);
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(new DefaultSimpleMetaDataModel(dataType))));
        } else {
            DefaultStructuredMetadataModel model = buildJSONMetaDataModel((JSONObjectType)itemsType);
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(model)));
        }

    }

    private void processJSONSchemaPrimitive(JSONType property, String name, List<MetaDataField> metadata) {
        DataType dataType = getDataType(property);
        metadata.add(new DefaultMetaDataField(name, new DefaultSimpleMetaDataModel(dataType)));
    }

    private DataType getDataType(JSONType jsonType) {

        DataType dataType = typeMapping.get(jsonType);
        if(dataType!=null){
            return dataType;
        }
        return DataType.STRING;
    }

}
