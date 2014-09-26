package org.mule.common.metadata;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class JSONSchemaMetadataModelFactory
{

    public static final String OBJECT_ELEMENT_NAME = "object";
    public static final String ARRAY_ELEMENT_NAME = "array";
    public static final String TYPE = "type";
    public static final String PROPERTIES = "properties";

    public JSONSchemaMetadataModelFactory()
    {

    }

    private MetaDataModel buildModel(String jsonSchemaString, URL jsonSchemaURL)
    {
        try
        {
            JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);
            if (jsonSchemaObject.has(TYPE) && jsonSchemaObject.get(TYPE).toString().toLowerCase().equals(ARRAY_ELEMENT_NAME))
            {

                final JSONArrayType arrayType = new JSONArrayType(new SchemaEnv(jsonSchemaObject, jsonSchemaURL), jsonSchemaObject);
                final JSONType itemsType = arrayType.getItemsType();
                if (itemsType.isJSONObject())
                {
                    final DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON, new JSONSchemaMetaDataFieldFactory((JSONObjectType) itemsType));
                    return new DefaultListMetaDataModel(model);
                }
                else if (itemsType.isJSONPrimitive())
                {
                    final DataType dataType = JSONTypeUtils.getDataType(itemsType);
                    final MetaDataModel model = dataType == DataType.UNKNOWN ? new DefaultUnknownMetaDataModel() : new DefaultSimpleMetaDataModel(dataType);
                    return new DefaultListMetaDataModel(model);
                }
                else
                {
                    final DefaultUnknownMetaDataModel model = new DefaultUnknownMetaDataModel();
                    return new DefaultListMetaDataModel(model);
                }

            }
            else if ((jsonSchemaObject.has(TYPE) && jsonSchemaObject.get(TYPE).toString().toLowerCase().equals(OBJECT_ELEMENT_NAME)) || jsonSchemaObject.has(PROPERTIES))
            {
                final JSONSchemaMetaDataFieldFactory fieldFactory = new JSONSchemaMetaDataFieldFactory(new JSONObjectType(new SchemaEnv(jsonSchemaObject, jsonSchemaURL), jsonSchemaObject));
                return new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory);
            }
            else
            {
                //e.g.: Case root's type is an array.
                return new DefaultUnknownMetaDataModel();
            }

        }
        catch (SchemaException e)
        {
            throw new MetaDataGenerationException(e);
        }
    }

    public MetaDataModel buildModel(String jsonSchemaString)
    {
        return buildModel(jsonSchemaString, null);
    }

    public MetaDataModel buildModel(URL url) throws IOException
    {
        String jsonSchemaString = IOUtils.toString(url.openStream());
        return buildModel(jsonSchemaString, url);
    }
}
