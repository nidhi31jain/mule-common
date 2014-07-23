package org.mule.common.metadata.test.json.schema;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

import java.io.InputStream;

/**
 * Created by studio on 21/07/2014.
 */
public class JSONSchemaMetaDataModelTest {

    JSONSchemaMetadataModelFactory modelFactory = new JSONSchemaMetadataModelFactory();


    @Rule
    public ExpectedException schemaException = ExpectedException.none();

    @Test
    public void testDefinitionsReferences() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithDefinitions.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("resource"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));

        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().size(), CoreMatchers.is(2));

        //Test hints field
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(0).getName(), CoreMatchers.is("hints"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));

        //Test href field
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(1).getName(), CoreMatchers.is("href"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        //Test hints field's fields
        Assert.assertThat(((DefaultStructuredMetadataModel)((DefaultStructuredMetadataModel) model.getFields().get(0).getMetaDataModel()).getFields().get(0).getMetaDataModel()).getFields().size(), CoreMatchers.is(11));


    }

    @Test
    public void testCircularReferenceWithNameAttribute() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithCircularReferenceWithNameAttribute.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
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

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("etc"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(1).getName(), CoreMatchers.is("friend"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(2));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));
    }

    @Test
    public void whenSchemaHasNoTypeAndNoPropertiesAttributesInTheRootSchemaItShouldThrowSchemaException() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithOneOf.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);
        JSONObject jsonSchemaObject = new JSONObject(jsonSchemaString);

        schemaException.expect(SchemaException.class);

        @SuppressWarnings("UnusedDeclaration")
        JSONObjectType jsonSchemaObjectType = new JSONObjectType(new SchemaEnv(null, jsonSchemaObject), jsonSchemaObject);
    }

    @Test
    public void whenAPropertyHasNoTypeAndNoPropertiesAttributesAndItHasOneOfOrAllOfOrAnyOfThenItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithCircularReferenceWithRefAttribute.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("etc"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(1).getName(), CoreMatchers.is("friend"));
        Assert.assertThat(((DefaultStructuredMetadataModel) model.getFields().get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(2));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));
    }

    @Test
    public void whenItemsPropertyOfAnArrayHasNoTypeAndNoPropertiesAttributesAndItHasOneOfThenItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAnArrayAndAllOfTagInItemsProperty.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        DefaultListMetaDataModel model = (DefaultListMetaDataModel) metaDataModel;

        Assert.assertThat(model.getElementModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getElementModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));
        Assert.assertThat(model.getDataType(), CoreMatchers.is(DataType.LIST));

    }

    @Test
    public void whenAnObjectPropertyHasNoTypeAndNoPropertiesAttributesAndItHasAllOfThenItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAllOfInAProperty.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("value"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));

    }

    @Test
    public void whenAnObjectPropertyHasAnArrayInItsTypeItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAPropertyWithAnArrayType.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("value"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));

    }

    @Test
    public void whenRootTypeIsAnArrayItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAnArrayType.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(metaDataModel.getDataType(), CoreMatchers.is(DataType.UNKNOWN));
    }

    @Test
    public void testArrayOfStrings() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaArrayOfStrings.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        DefaultListMetaDataModel model = (DefaultListMetaDataModel) metaDataModel;

        Assert.assertThat(model.getDataType(), CoreMatchers.is(DataType.LIST));
        Assert.assertThat(model.getElementModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getElementModel().getDataType(), CoreMatchers.is(DataType.STRING));

    }

    @Test
    public void integrationTest() throws Exception{
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaIntegration.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        MetaDataModel metaDataModel = modelFactory.buildModel(jsonSchemaString);

        Assert.assertThat(metaDataModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel model = (DefaultStructuredMetadataModel) metaDataModel;

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(8));

        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("apiVersion"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(1).getName(), CoreMatchers.is("apis"));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(model.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.LIST));

        DefaultListMetaDataModel listMetaDataModel = (DefaultListMetaDataModel) model.getFields().get(1).getMetaDataModel();
        MetaDataModel elementModel = listMetaDataModel.getElementModel();
        Assert.assertThat(elementModel, CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        DefaultStructuredMetadataModel structuredElementModel = (DefaultStructuredMetadataModel) elementModel;

        Assert.assertThat(structuredElementModel.getFields().size(), CoreMatchers.is(3));
        Assert.assertThat(structuredElementModel.getFields().get(0).getName(), CoreMatchers.is("description"));
        Assert.assertThat(structuredElementModel.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(structuredElementModel.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(structuredElementModel.getFields().get(1).getName(), CoreMatchers.is("operations"));
        Assert.assertThat(structuredElementModel.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(structuredElementModel.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.LIST));

        Assert.assertThat(((DefaultListMetaDataModel)structuredElementModel.getFields().get(1).getMetaDataModel()).getElementModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)structuredElementModel.getFields().get(1).getMetaDataModel()).getElementModel().getDataType(), CoreMatchers.is(DataType.NUMBER));

        Assert.assertThat(model.getFields().get(2).getName(), CoreMatchers.is("basePath"));
        Assert.assertThat(model.getFields().get(2).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(2).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(3).getName(), CoreMatchers.is("consumes"));
        Assert.assertThat(model.getFields().get(3).getMetaDataModel(), CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(model.getFields().get(3).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.LIST));
        Assert.assertThat(((DefaultListMetaDataModel)model.getFields().get(3).getMetaDataModel()).getElementModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)model.getFields().get(3).getMetaDataModel()).getElementModel().getDataType(), CoreMatchers.is(DataType.STRING));

        Assert.assertThat(model.getFields().get(4).getName(), CoreMatchers.is("models"));
        Assert.assertThat(model.getFields().get(4).getMetaDataModel(), CoreMatchers.instanceOf(DefaultStructuredMetadataModel.class));
        Assert.assertThat(model.getFields().get(4).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.JSON));

        Assert.assertThat(model.getFields().get(7).getName(), CoreMatchers.is("swaggerVersion"));
        Assert.assertThat(model.getFields().get(7).getMetaDataModel(), CoreMatchers.instanceOf(DefaultSimpleMetaDataModel.class));
        Assert.assertThat(model.getFields().get(7).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));
    }

    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
