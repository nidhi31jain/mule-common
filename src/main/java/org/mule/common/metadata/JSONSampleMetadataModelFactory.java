package org.mule.common.metadata;

import com.google.gson.*;
import org.apache.commons.io.IOUtils;
import org.mule.common.metadata.datatype.DataType;

import java.io.IOException;
import java.net.URL;

public class JSONSampleMetadataModelFactory  {

    public JSONSampleMetadataModelFactory() {
    }

    public MetaDataModel buildModel(URL url) throws IOException {
        String json = IOUtils.toString(url.openStream());
        return buildModel(json);
    }

    public MetaDataModel buildModel(String json) {
        JsonElement root = new JsonParser().parse(json);
        if (root.isJsonObject()) {
            JSONSampleMetaDataFieldFactory fieldFactory = new JSONSampleMetaDataFieldFactory((JsonObject) root);
            return new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory);
        } else {
            JsonElement child = JSONMetaDataHelper.getFirstChild((JsonArray) root);
            // If the array is empty we assume String type
            DataType dataType = child == null ? DataType.STRING : child.isJsonPrimitive() ? JSONMetaDataHelper.getType((JsonPrimitive) child) : DataType.JSON;
            return new DefaultListMetaDataModel(dataType == DataType.JSON ?  new DefaultStructuredMetadataModel(DataType.JSON, new JSONSampleMetaDataFieldFactory((JsonObject) child)) : new DefaultSimpleMetaDataModel(dataType));
        }
    }
}
