/**
 *
 */
package org.mule.common.metadata.builder;

import java.util.ArrayList;
import java.util.List;

import org.mule.common.metadata.DefaultMetaDataField;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.field.property.DefaultFieldPropertyFactory;
import org.mule.common.metadata.field.property.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.DsqlWhereMetaDataFieldProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;
import org.mule.common.query.expression.EqualsOperator;
import org.mule.common.query.expression.GreaterOperator;
import org.mule.common.query.expression.GreaterOrEqualsOperator;
import org.mule.common.query.expression.LessOperator;
import org.mule.common.query.expression.LessOrEqualsOperator;
import org.mule.common.query.expression.LikeOperator;
import org.mule.common.query.expression.NotEqualsOperator;
import org.mule.common.query.expression.Operator;

public class DefaultMetaDataFieldBuilder implements MetaDataFieldBuilder {

	private String name;
	private List<MetaDataFieldProperty> fieldProperties;
	private List<Class<? extends MetaDataFieldProperty>> undesiredFieldProperties;
	private MetaDataField.FieldAccessType accessType;
	private MetaDataBuilder builder;
	private List<Operator> supportedOperators;

	DefaultMetaDataFieldBuilder(String name, MetaDataBuilder builder) {
		this.name = name;
		this.builder = builder;
		this.fieldProperties = new ArrayList<MetaDataFieldProperty>();
		this.supportedOperators = new ArrayList<Operator>();
		this.undesiredFieldProperties = new ArrayList<Class<? extends MetaDataFieldProperty>>();
	}

	public MetaDataFieldBuilder isSelectCapable(boolean capable) {

		if (capable) {
			fieldProperties.add(new DsqlSelectMetaDataFieldProperty());
		} else {
			undesiredFieldProperties.add(DsqlSelectMetaDataFieldProperty.class);
		}
		return this;
	}

	public MetaDataFieldBuilder isOrderByCapable(boolean capable) {
		if (capable) {
			fieldProperties.add(new DsqlOrderMetaDataFieldProperty());
		} else {
			undesiredFieldProperties.add(DsqlOrderMetaDataFieldProperty.class);
		}
		return this;
	}

	public void isWhereCapable(boolean capable) {
		if (capable) {
			fieldProperties.add(new DsqlWhereMetaDataFieldProperty());
		} else {
			undesiredFieldProperties.add(DsqlWhereMetaDataFieldProperty.class);
		}
	}

	public void supportsEquals() {
		supportedOperators.add(new EqualsOperator());
	}

	public void supportsNotEquals() {
		supportedOperators.add(new NotEqualsOperator());
	}

	public void supportsGreater() {
		supportedOperators.add(new GreaterOperator());
	}

	public void supportsGreaterOrEquals() {
		supportedOperators.add(new GreaterOrEqualsOperator());
	}

	public void supportsLess() {
		supportedOperators.add(new LessOperator());
	}

	public void supportsLessOrEquals() {
		supportedOperators.add(new LessOrEqualsOperator());
	}

	public void supportsLike() {
		supportedOperators.add(new LikeOperator());
	}

	public void withAccessType(MetaDataField.FieldAccessType accessType) {
		this.accessType = accessType;
	}

	public MetaDataField build() {

		DefaultFieldPropertyFactory defaultFieldPropertyFactory = new DefaultFieldPropertyFactory();
		MetaDataModel model = builder.build();

		// add default properties if empty
		List<MetaDataFieldProperty> finalFieldProperties = new ArrayList<MetaDataFieldProperty>();
		if (fieldProperties.isEmpty()) {
			fieldProperties.addAll(defaultFieldPropertyFactory.getProperties("null", model));
		} else {
			if (!supportedOperators.isEmpty()) {
				fieldProperties.add(new DsqlQueryOperatorsMetaDataFieldProperty(supportedOperators));
			}
		}

		// filter undesired properties
		for (MetaDataFieldProperty property : fieldProperties) {
			if (!undesiredFieldProperties.contains(property.getClass())) {
				finalFieldProperties.add(property);
			}
		}

		if (accessType == null) {
			accessType = MetaDataField.FieldAccessType.READ_WRITE;
		}
		return new DefaultMetaDataField(name, model, accessType, fieldProperties);
	}

}
