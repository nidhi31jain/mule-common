package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

/**
 * Created by studio on 18/07/2014.
 */
public class JSONSchemaMetaDataFieldFactory implements MetaDataFieldFactory {

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

    Map<JSONObjectType, DefaultStructuredMetadataModel> visitedTypes = null;

    private JSONType jsonSchemaType;
    protected static final String OBJECT_ELEMENT_NAME = "object";
    public static final String ARRAY_ELEMENT_NAME = "array";

    public JSONSchemaMetaDataFieldFactory(String jsonSchemaString) throws SchemaException {
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);
        SchemaEnv schemaEnv = new SchemaEnv(null, jsonSchemaObject);
        if (jsonSchemaObject.has("type") && jsonSchemaObject.get("type").toString().toLowerCase().equals(ARRAY_ELEMENT_NAME)) {
            jsonSchemaType = new JSONArrayType(schemaEnv, jsonSchemaObject);
        } else if(jsonSchemaObject.has("type") && jsonSchemaObject.get("type") instanceof JSONArray){//Case root's type is an array.
            jsonSchemaType = new JSONType.Everything();
        } else {
            jsonSchemaType = new JSONObjectType(schemaEnv, jsonSchemaObject);
        }

    }

    public JSONSchemaMetaDataFieldFactory(JSONObjectType type) {
        jsonSchemaType = type;
    }

    public JSONSchemaMetaDataFieldFactory(JSONObjectType type, Map<JSONObjectType, DefaultStructuredMetadataModel> visitedTypesParameter) {
        jsonSchemaType = type;
        visitedTypes = visitedTypesParameter;
    }

    public List<MetaDataField> createFields() throws Exception {
        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();

        if (visitedTypes == null) {
            visitedTypes = new HashMap<JSONObjectType, DefaultStructuredMetadataModel>();
        }

        if (jsonSchemaType.isJSONObject()) {
            loadFields((JSONObjectType) jsonSchemaType, metaDataFields);
        } else if(jsonSchemaType.isJSONArray()){
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
        } else if(property.isJSONPointer()){
            processJSONPointer((JSONPointerType)property, name, metadata);
        }
    }

    private void processJSONSchemaObject(JSONObjectType type, String name, List<MetaDataField> metadata) throws Exception {

        DefaultStructuredMetadataModel model = buildJSONMetaDataModel(type);
        metadata.add(new DefaultMetaDataField(name, model));

    }

    private DefaultStructuredMetadataModel buildJSONMetaDataModel(JSONObjectType type) throws Exception {

        DefaultStructuredMetadataModel model;
        if (visitedTypes.containsKey(type)) {
            model = visitedTypes.get(type);
        } else {
            model = new DefaultStructuredMetadataModel(DataType.JSON);
            visitedTypes.put(type, model);
            model.init(new JSONSchemaMetaDataFieldFactory(type, visitedTypes));
        }
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
            MetaDataModel model = dataType==DataType.UNKNOWN ? new DefaultUnknownMetaDataModel(): new DefaultSimpleMetaDataModel(dataType);
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(model)));
        }else {
            DefaultStructuredMetadataModel model = null;
            if(itemsType.isJSONPointer()){
                 model = buildJSONMetaDataModel((JSONObjectType) ((JSONPointerType) itemsType).resolve());
            }else if (itemsType.isJSONObject()){
                 model = buildJSONMetaDataModel((JSONObjectType) itemsType);
            }
            metadata.add(new DefaultMetaDataField(name, new DefaultListMetaDataModel(model)));
        }

    }

    private void processJSONSchemaPrimitive(JSONType property, String name, List<MetaDataField> metadata) {
        DataType dataType = getDataType(property);
        MetaDataModel model = dataType==DataType.UNKNOWN ? new DefaultUnknownMetaDataModel(): new DefaultSimpleMetaDataModel(dataType);
        metadata.add(new DefaultMetaDataField(name, model));
    }

    private void processJSONPointer(JSONPointerType ptr, String name, List<MetaDataField> metadata) throws Exception {
        processJSONSchemaElement(ptr.resolve(), name, metadata);
    }

    private DataType getDataType(JSONType jsonType) {

        DataType dataType = typeMapping.get(jsonType.getClass());
        if (dataType != null) {
            return dataType;
        }
        return DataType.STRING;
    }

}
