package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.query.Field;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MetaDataModelVisitor for fields filtering, only by name not by type
 */
public class FieldFilterVisitor implements MetaDataModelVisitor {

    private List<Field> fields;
    private MetaDataModel resultModel;

    public FieldFilterVisitor(List<Field> fields) {
        this.fields = fields;
        this.resultModel = new DefaultSimpleMetaDataModel(DataType.VOID);
    }


    @Override
    public void visitPojoModel(PojoMetaDataModel pojoMetaDataModel) {
        //DO NOTHING
    }

    @Override
    public void visitListMetaDataModel(ListMetaDataModel listMetaDataModel) {
        //DO NOTHING
    }

    @Override
    public void visitSimpleMetaDataModel(SimpleMetaDataModel simpleMetaDataModel) {
        //DO NOTHING
    }

    @Override
    public void visitStaticMapModel(ParameterizedMapMetaDataModel parameterizedMapMetaDataModel) {
        //DO NOTHING
    }

    @Override
    public void visitDynamicMapModel(DefinedMapMetaDataModel definedMapMetaDataModel) {
        if (fields == null) return;
        Map<String,MetaDataModel> newMapModel = new HashMap<String, MetaDataModel>();
        for(Field f: fields) {
            MetaDataModel fieldModel = definedMapMetaDataModel.getValueMetaDataModel(f.getName());
            if (fieldModel != null) {
                newMapModel.put(f.getName(),fieldModel);
            }
        }
        resultModel = new DefaultDefinedMapMetaDataModel(newMapModel,definedMapMetaDataModel.getName());
    }

    public MetaData filteringResult() {
        return new DefaultMetaData(resultModel);
    }
}
