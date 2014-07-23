package org.mule.common.metadata;

import org.json.JSONArray;
import org.json.JSONObject;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

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
            JSONType itemsType = arrayType.getItemsType();

            if(itemsType.isJSONObject()){
                DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
                model.init(new JSONSchemaMetaDataFieldFactory((JSONObjectType) itemsType));
                DefaultListMetaDataModel listModel = new DefaultListMetaDataModel(model);
                return listModel;
            }else if(itemsType.isJSONPrimitive()){
                DataType dataType = JSONTypeUtils.getDataType(itemsType);
                MetaDataModel model = dataType==DataType.UNKNOWN ? new DefaultUnknownMetaDataModel(): new DefaultSimpleMetaDataModel(dataType);
                DefaultListMetaDataModel listModel = new DefaultListMetaDataModel(model);
                return listModel;
            }else{
                DefaultUnknownMetaDataModel model = new DefaultUnknownMetaDataModel();
                DefaultListMetaDataModel listModel = new DefaultListMetaDataModel(model);
                return listModel;
            }

        } else if ((jsonSchemaObject.has("type") && jsonSchemaObject.get("type").toString().toLowerCase().equals(OBJECT_ELEMENT_NAME)) || jsonSchemaObject.has("properties")){
            DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
            model.init(new JSONSchemaMetaDataFieldFactory(new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject)));
            return model;
        }else {//e.g.: Case root's type is an array.
            DefaultUnknownMetaDataModel model = new DefaultUnknownMetaDataModel();
            return model;
        }

    }
}
