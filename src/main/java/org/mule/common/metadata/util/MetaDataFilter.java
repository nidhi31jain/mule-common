package org.mule.common.metadata.util;

import org.mule.common.metadata.FieldFilterVisitor;
import org.mule.common.metadata.MetaData;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.MetaDataModelVisitor;
import org.mule.common.query.Field;

import java.util.List;

/**
 * A class used to filter up fields from a metadata object
 */
public class MetaDataFilter {

    private MetaData metaData;
    private List<Field> fields;

    public MetaDataFilter(MetaData metaData, List<Field> fields) {
        this.metaData = metaData;
        this.fields = fields;
    }

    public MetaData filter(){
        MetaDataModel model = metaData.getPayload();
        MetaDataModelVisitor modelVisitor = new FieldFilterVisitor(fields);
        model.accept(modelVisitor);
        return metaData;

    }
}
