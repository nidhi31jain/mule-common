package org.mule.common.metadata.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.mule.common.metadata.DefaultDefinedMapMetaDataModel;
import org.mule.common.metadata.DefaultSimpleMetaDataModel;
import org.mule.common.metadata.DefinedMapMetaDataModel;
import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.PojoMetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.DynamicObjectBuilder;
import org.mule.common.metadata.builder.ListMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.metadata.field.property.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlWhereMetaDataFieldProperty;
import org.mule.common.metadata.test.pojo.EverythingPojo;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.LikeOperator;
import org.mule.common.query.expression.Operator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 */
public class DefaultDefinedMapMetaDataModelTestCase {

	private DefinedMapMetaDataModel mapMetaDataModel;

	@Before
	public void setUp() {
		Map<String, MetaDataModel> map = new HashMap<String, MetaDataModel>();
		map.put("field1", new DefaultSimpleMetaDataModel(DataType.STRING));
		map.put("field2", new DefaultSimpleMetaDataModel(DataType.NUMBER));

		mapMetaDataModel = new DefaultDefinedMapMetaDataModel(map, "mapName");
	}

	@Test
	public void testKeys() {
		assertTrue(mapMetaDataModel.getKeys().contains("field1"));
		assertTrue(mapMetaDataModel.getKeys().contains("field2"));
	}

	@Test
	public void testValues() {
		assertTrue(mapMetaDataModel.getValueMetaDataModel("field1").getDataType().equals(DataType.STRING));
		assertTrue(mapMetaDataModel.getValueMetaDataModel("field2").getDataType().equals(DataType.NUMBER));
	}

	@Test
	public void testFields() {
		assertNotNull(mapMetaDataModel.getFields());
		assertEquals(2, mapMetaDataModel.getFields().size());
		assertTrue(mapMetaDataModel.getFields().get(1).getMetaDataModel().getDataType().equals(DataType.STRING));
		assertTrue(mapMetaDataModel.getFields().get(0).getMetaDataModel().getDataType().equals(DataType.NUMBER));
	}

	@Test
	public void whenCreatingModelFromMapsWithMapsFieldsShouldBeCreatedCorrectly() {

		final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleProperty", DataType.STRING).addDynamicObjectField("subComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
				.addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING);

		final DefinedMapMetaDataModel metaDataModel = container.build();
		final List<MetaDataField> fields = metaDataModel.getFields();
		Assert.assertThat(fields.size(), CoreMatchers.is(2));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("subComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

		final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) metaDataModel.getValueMetaDataModel("subComplexProperty");
		Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(2));

		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

		final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

		Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
		Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));

	}

	@Test
	public void whenCreatingModelFromMapsWithListOfMapsFieldsShouldBeCreatedCorrectly() {

		final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleProperty", DataType.STRING).addListOfDynamicObjectField("subListComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
				.addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING);

		final DefinedMapMetaDataModel metaDataModel = container.build();
		final List<MetaDataField> fields = metaDataModel.getFields();
		Assert.assertThat(fields.size(), CoreMatchers.is(2));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("subListComplexProperty").getDataType(), CoreMatchers.is(DataType.LIST));

		final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) ((ListMetaDataModel) metaDataModel.getValueMetaDataModel("subListComplexProperty"))
				.getElementModel();
		Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(2));

		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));

		final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

		Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
		Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));

	}

	@Test
	public void whenCreatingComplexStructureFieldsShouldBeCreatedCorrectly() {

		final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleProperty", DataType.STRING).addListOfDynamicObjectField("subListComplexProperty").addSimpleField("subSimpleProperty", DataType.STRING)
				.addDynamicObjectField("subSubComplexProperty").addSimpleField("subSubSimpleProperty", DataType.STRING).endDynamicObject()
				.addSimpleField("subSimpleProperty2", DataType.STRING).endDynamicObject();

		final DefinedMapMetaDataModel metaDataModel = container.build();
		final List<MetaDataField> fields = metaDataModel.getFields();
		Assert.assertThat(fields.size(), CoreMatchers.is(2));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("simpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(metaDataModel.getValueMetaDataModel("subListComplexProperty").getDataType(), CoreMatchers.is(DataType.LIST));

		final DefinedMapMetaDataModel subComplexProperty = (DefinedMapMetaDataModel) ((ListMetaDataModel) metaDataModel.getValueMetaDataModel("subListComplexProperty"))
				.getElementModel();
		Assert.assertThat(subComplexProperty.getFields().size(), CoreMatchers.is(3));

		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));
		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSubComplexProperty").getDataType(), CoreMatchers.is(DataType.MAP));
		Assert.assertThat(subComplexProperty.getValueMetaDataModel("subSimpleProperty2").getDataType(), CoreMatchers.is(DataType.STRING));

		final DefinedMapMetaDataModel subSubComplexProperty = (DefinedMapMetaDataModel) subComplexProperty.getValueMetaDataModel("subSubComplexProperty");

		Assert.assertThat(subSubComplexProperty.getFields().size(), CoreMatchers.is(1));
		Assert.assertThat(subSubComplexProperty.getValueMetaDataModel("subSubSimpleProperty").getDataType(), CoreMatchers.is(DataType.STRING));

	}

	@Test
	public void whenCreatingMapDefaultImplClassShouldBeSet() {
		final DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		DefinedMapMetaDataModel model = container.build();
		Assert.assertThat(model.getImplementationClass(), CoreMatchers.is("java.util.HashMap"));
	}

	@Test
	public void whenCreatingListDefaultImplClassShouldBeSet() {
		final ListMetaDataBuilder<?> container = new DefaultMetaDataBuilder().createList();
		container.ofPojo(Object.class);
		ListMetaDataModel model = container.build();
		Assert.assertThat(model.getImplementationClass(), CoreMatchers.is("java.util.ArrayList"));
	}

	@Test
	public void whenCreatingFieldsDefaultPropertiesShouldBeCreated() {
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.STRING, "java.lang.String");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.BOOLEAN, "java.lang.Boolean");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.BYTE, "java.lang.Byte");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.DATE, "java.util.Date");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.DATE_TIME, "java.util.Calendar");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.ENUM, "java.lang.Enum");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.NUMBER, "java.lang.Number");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.STREAM, "java.io.InputStream");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.STRING, "java.lang.String");
		parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType.VOID, "java.lang.Void");
	}

	private void parameterizedWhenCreatingFieldsDefaultPropertiesShouldBeCreated(DataType dataType, String expectedImplClassName) {
		DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleField", dataType).endDynamicObject();
		DefinedMapMetaDataModel metaDataModel = container.build();

		Assert.assertThat(metaDataModel.getFields().size(), CoreMatchers.is(1));
		MetaDataField firstField = metaDataModel.getFields().get(0);
		Assert.assertThat(firstField.getName(), CoreMatchers.is("simpleField"));
		Assert.assertTrue(firstField.hasProperty(DsqlSelectMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlOrderMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlWhereMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class));

		DsqlQueryOperatorsMetaDataFieldProperty supportedOperatorsProperty = firstField.getProperty(DsqlQueryOperatorsMetaDataFieldProperty.class);
		Assert.assertThat(supportedOperatorsProperty.getSupportedOperators(), CoreMatchers.equalTo(SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(dataType)));

		Assert.assertThat(firstField.getMetaDataModel().getImplementationClass(), CoreMatchers.is(expectedImplClassName));
	}

	@Test
	public void whenSpecifyingPropertiesOnlyThoseShouldBeCreated() {
		DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleField", DataType.STRING).isWhereCapable(true);
		DefinedMapMetaDataModel metaDataModel = container.build();

		Assert.assertThat(metaDataModel.getFields().size(), CoreMatchers.is(1));
		MetaDataField firstField = metaDataModel.getFields().get(0);
		Assert.assertThat(firstField.getName(), CoreMatchers.is("simpleField"));
		Assert.assertTrue(firstField.hasProperty(DsqlWhereMetaDataFieldProperty.class));
		Assert.assertFalse(firstField.hasProperty(DsqlSelectMetaDataFieldProperty.class));
		Assert.assertFalse(firstField.hasProperty(DsqlOrderMetaDataFieldProperty.class));
		Assert.assertFalse(firstField.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class));
	}

	@Test
	public void whenSpecifyingOperatorsOnlyThoseShouldBeCreated() {
		DynamicObjectBuilder<?> container = new DefaultMetaDataBuilder().createDynamicObject("Container");
		container.addSimpleField("simpleField", DataType.STRING).isWhereCapable(true).withSpecificOperations().supportsEquals().supportsLike();
		DefinedMapMetaDataModel metaDataModel = container.build();

		Assert.assertThat(metaDataModel.getFields().size(), CoreMatchers.is(1));
		MetaDataField firstField = metaDataModel.getFields().get(0);
		Assert.assertThat(firstField.getName(), CoreMatchers.is("simpleField"));
		Assert.assertTrue(firstField.hasProperty(DsqlWhereMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class));
		Assert.assertFalse(firstField.hasProperty(DsqlSelectMetaDataFieldProperty.class));
		Assert.assertFalse(firstField.hasProperty(DsqlOrderMetaDataFieldProperty.class));

		DsqlQueryOperatorsMetaDataFieldProperty property = firstField.getProperty(DsqlQueryOperatorsMetaDataFieldProperty.class);
		List<Operator> supportedOperators = property.getSupportedOperators();

		Assert.assertThat(supportedOperators.size(), CoreMatchers.is(2));
		Assert.assertTrue(supportedOperators.contains(new EqualsOperator()));
		Assert.assertTrue(supportedOperators.contains(new LikeOperator()));
	}

	@Test
	public void whenSpecifyingAFieldIsNotCapableOfSomethingThePropertyShouldNotBeCreatedButTheRestOfTheDefaultsShould() {
		DefinedMapMetaDataModel metaDataModel = new DefaultMetaDataBuilder().createDynamicObject("Container") //
				.addSimpleField("simpleField", DataType.STRING) //
				.isWhereCapable(false) //
				.build();

		Assert.assertThat(metaDataModel.getFields().size(), CoreMatchers.is(1));
		MetaDataField firstField = metaDataModel.getFields().get(0);
		Assert.assertThat(firstField.getName(), CoreMatchers.is("simpleField"));
		Assert.assertFalse(firstField.hasProperty(DsqlWhereMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlQueryOperatorsMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlSelectMetaDataFieldProperty.class));
		Assert.assertTrue(firstField.hasProperty(DsqlOrderMetaDataFieldProperty.class));
	}

	@Test
	public void testListOfPojos() {
		ListMetaDataModel result = new DefaultMetaDataBuilder().createList().ofPojo(EverythingPojo.class).endPojo().build();
		Assert.assertThat(result.getElementModel(), CoreMatchers.is(PojoMetaDataModel.class));
		Assert.assertThat(((PojoMetaDataModel) result.getElementModel()).getFields().size(), CoreMatchers.is(17));

	}

}
