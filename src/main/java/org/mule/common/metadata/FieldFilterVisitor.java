package org.mule.common.metadata;

import org.mule.common.query.Field;

import java.util.List;

/**
 * MetaDataModelVisitor for fields filtering, only by name not by type
 */
public class FieldFilterVisitor implements MetaDataModelVisitor {

    private List<Field> fields;

    public FieldFilterVisitor(List<Field> fields) {
        this.fields = fields;
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
        for(Field f: fields) {
            if (definedMapMetaDataModel.getKeys().contains(f.getName())) {
                definedMapMetaDataModel.getKeys().remove(f.getName());
            }
        }
    }
}
