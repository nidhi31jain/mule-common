package org.mule.common.metadata.builder;

import org.mule.common.metadata.JSONSampleMetadataModelFactory;
import org.mule.common.metadata.JSONSchemaMetadataModelFactory;
import org.mule.common.metadata.MetaDataModel;


public class DefaultJSONMetaDataBuilder<P extends MetaDataBuilder<?>> implements JSONMetaDataBuilder<P> {
    private String json;
    private String jsonSchema;

    public DefaultJSONMetaDataBuilder() {
    }

    @Override
    public JSONMetaDataBuilder<P> setExample(String json) {
        this.json = json;
        return this;
    }

    @Override
    public JSONMetaDataBuilder<P> setSchema(String jsonSchema) {
        this.jsonSchema = jsonSchema;
        return this;
    }

    @Override
    public MetaDataModel build() {
        MetaDataModel result = null;
        if (json != null) {
            result = new JSONSampleMetadataModelFactory().buildModel(json);
        } else if (jsonSchema != null) {
            result = new JSONSchemaMetadataModelFactory().buildModel(jsonSchema);
        }
        return result;
    }
}
