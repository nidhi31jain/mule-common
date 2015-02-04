package org.mule.common.metadata;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readValue(json, JsonNode.class);
            if (root.isObject()) {
                JSONSampleMetaDataFieldFactory fieldFactory = new JSONSampleMetaDataFieldFactory((ObjectNode) root);
                return new DefaultStructuredMetadataModel(DataType.JSON, fieldFactory);
            } else {
                JsonNode child = JSONMetaDataHelper.getFirstChild((ArrayNode) root);
                // If the array is empty we assume String type
                DataType dataType = child == null ? DataType.STRING : !child.isObject() ? JSONMetaDataHelper.getType(child) : DataType.JSON;
                return new DefaultListMetaDataModel(dataType == DataType.JSON ?  new DefaultStructuredMetadataModel(DataType.JSON, new JSONSampleMetaDataFieldFactory((ObjectNode) child)) : new DefaultSimpleMetaDataModel(dataType));
            }

        } catch (IOException e) {
            throw new MetaDataGenerationException(e);
        }
    }
}
