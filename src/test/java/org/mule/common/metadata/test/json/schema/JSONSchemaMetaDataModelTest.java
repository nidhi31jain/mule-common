package org.mule.common.metadata.test.json.schema;

import org.hamcrest.CoreMatchers;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.parser.json.*;

import java.io.InputStream;

/**
 * Created by studio on 21/07/2014.
 */
public class JSONSchemaMetaDataModelTest {

    @Rule
    public ExpectedException schemaException = ExpectedException.none();

    @Test
    public void testDefinitionsReferences() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithDefinitions.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

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

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

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

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

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

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

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

        JSONSchemaMetadataModelFactory factory = new JSONSchemaMetadataModelFactory();
//        MetaDataModel model2 = factory.buildModel(jsonSchemaString);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        DefaultListMetaDataModel listModel = new DefaultListMetaDataModel(model);

        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultListMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("array"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.LIST));

        Assert.assertThat(((DefaultListMetaDataModel)model.getFields().get(0).getMetaDataModel()).getElementModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(((DefaultListMetaDataModel)model.getFields().get(0).getMetaDataModel()).getElementModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));
    }

    @Test
    public void whenAnObjectPropertyHasNoTypeAndNoPropertiesAttributesAndItHasAllOfThenItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAllOfInAProperty.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("value"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));

    }

    @Test
    public void whenAnObjectPropertyHasAnArrayInItsTypeItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAPropertyWithAnArrayType.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("value"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));

    }

    @Test
    @Ignore
    public void whenRootTypeIsAnArrayItShouldHaveAnUnknownModelAssociated() throws Exception {
        InputStream jsonSchemaStream = getClass().getClassLoader().getResourceAsStream("jsonSchemaWithAnArrayType.json");
        String jsonSchemaString = convertStreamToString(jsonSchemaStream);

        DefaultStructuredMetadataModel model = new DefaultStructuredMetadataModel(DataType.JSON);
        model.init(new JSONSchemaMetaDataFieldFactory(jsonSchemaString));

        Assert.assertThat(model.getFields().size(), CoreMatchers.is(0));

        Assert.assertThat(model.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(DefaultUnknownMetaDataModel.class));
        Assert.assertThat(model.getFields().get(0).getName(), CoreMatchers.is("value"));
        Assert.assertThat(model.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.UNKNOWN));

    }




    static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }


}
