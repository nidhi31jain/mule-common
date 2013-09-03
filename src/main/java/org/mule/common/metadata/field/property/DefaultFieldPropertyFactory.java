package org.mule.common.metadata.field.property;

import java.util.ArrayList;
import java.util.List;

import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.datatype.SupportedOperatorsFactory;
import org.mule.common.metadata.field.property.dsql.DsqlOrderMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlQueryOperatorsMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlSelectMetaDataFieldProperty;
import org.mule.common.metadata.field.property.dsql.DsqlWhereMetaDataFieldProperty;

/**
 * This object is responsible for retrieving the default properties for a future given field
 */
public class DefaultFieldPropertyFactory implements FieldPropertyFactory {
    @Override
    public List<MetaDataFieldProperty> getProperties(String name, MetaDataModel metaDataModel) {
    	// name parameter not used
        List<MetaDataFieldProperty> fieldProperties = new ArrayList<MetaDataFieldProperty>();
		fieldProperties.add(new DsqlSelectMetaDataFieldProperty());
		fieldProperties.add(new DsqlWhereMetaDataFieldProperty());
		fieldProperties.add(new DsqlOrderMetaDataFieldProperty());
		fieldProperties.add(new DsqlQueryOperatorsMetaDataFieldProperty(
				SupportedOperatorsFactory.getInstance().getSupportedOperationsFor(metaDataModel.getDataType())));
        return fieldProperties;
    }
}
