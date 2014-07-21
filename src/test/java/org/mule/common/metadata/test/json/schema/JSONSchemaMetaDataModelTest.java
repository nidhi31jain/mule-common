package org.mule.common.metadata.test.json.schema;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.mule.common.metadata.DefaultStructuredMetadataModel;
import org.mule.common.metadata.JSONSchemaMetaDataFieldFactory;
import org.mule.common.metadata.SimpleMetaDataModel;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.JSONObjectType;
import org.mule.common.metadata.parser.json.SchemaEnv;

import java.io.InputStream;

/**
 * Created by studio on 21/07/2014.
 */
public class JSONSchemaMetaDataModelTest {

    @Test
    public void testDefinitionsReferences() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithDefinitions.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        JSONObjectType jsonSchemaObjectType = new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);

        DefaultStructuredMetadataModel defaultStructuredMetadataModel = new DefaultStructuredMetadataModel(DataType.JSON);
        defaultStructuredMetadataModel.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaObjectType));


    }

    @Test
    public void testCircularReferenceWithNameAttribute() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithCircularReferenceWithNameAttribute.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        JSONObjectType jsonSchemaObjectType = new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaObjectType));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("etc"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(1).getName(), CoreMatchers.is("friend"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(2));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));
    }

    @Test
    public void testCircularReferenceWithRefAttribute() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithCircularReferenceWithRefAttribute.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        JSONObjectType jsonSchemaObjectType = new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaObjectType));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("etc"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(1).getName(), CoreMatchers.is("friend"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(2));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));
    }


    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
