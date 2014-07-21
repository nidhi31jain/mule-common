package org.mule.common.metadata.test.json.schema;

import org.json.JSONObject;
import org.junit.Test;
import org.mule.common.metadata.DefaultStructuredMetadataModel;
import org.mule.common.metadata.JSONSchemaMetaDataFieldFactory;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.JSONObjectType;
import org.mule.common.metadata.parser.json.SchemaEnv;
import sun.misc.IOUtils;

import java.io.InputStream;
import java.io.StringWriter;

/**
 * Created by studio on 21/07/2014.
 */
public class JSONSchemaMetaDataModelTest {

    @Test
    public void testDefinitions() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithDefinitions.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        JSONObjectType jsonSchemaObjectType = new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);

        DefaultStructuredMetadataModel defaultStructuredMetadataModel = new DefaultStructuredMetadataModel(DataType.JSON);
        defaultStructuredMetadataModel.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaObjectType));


    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
