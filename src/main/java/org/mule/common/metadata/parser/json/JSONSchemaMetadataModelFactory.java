package org.mule.common.metadata.parser.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;

/**
 * Created by studio on 22/07/2014.
 */
public class JSONSchemaMetadataModelFactory {

    protected static final String OBJECT_ELEMENT_NAME = "object";
    public static final String ARRAY_ELEMENT_NAME = "array";

    public JSONSchemaMetadataModelFactory() {
    }

    public AbstractMetaDataModel buildModel(String jsonSchemaString) throws Exception {
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        if (jsonSchemaObject.has("type") && jsonSchemaObject.get("type").toString().toLowerCase().equals(ARRAY_ELEMENT_NAME)) {
            JSONArrayType arrayType = new JSONArrayType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);

            DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
            model.init(new JSONSchemaMetaDataFieldFactory((JSONObjectType) arrayType.getItemsType()));
            DefaultListMetaDataModel listModel = new DefaultListMetaDataModel(model);
            return listModel;
        } else if(jsonSchemaObject.has("type") && jsonSchemaObject.get("type") instanceof JSONArray){//Case root's type is an array.
            DefaultUnknownMetaDataModel model = new DefaultUnknownMetaDataModel();
            return model;
        } else {
            DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
            model.init(new JSONSchemaMetaDataFieldFactory(new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject)));
            return model;
        }
    }
}
