/**
 *
 */
package org.mule.common.metadata;

public abstract class DefaultMetaDataModelVisitor implements MetaDataModelVisitor
{

    @Override
    public void visitPojoModel(PojoMetaDataModel pojoMetaDataModel)
    {
    }

    @Override
    public void visitListMetaDataModel(ListMetaDataModel listMetaDataModel)
    {
    }

    @Override
    public void visitSimpleMetaDataModel(SimpleMetaDataModel simpleMetaDataModel)
    {
    }

    @Override
    public void visitStaticMapModel(ParameterizedMapMetaDataModel parameterizedMapMetaDataModel)
    {
    }

    @Override
    public void visitDynamicMapModel(DefinedMapMetaDataModel definedMapMetaDataModel)
    {
    }

    @Override
    public void visitXmlMetaDataModel(XmlMetaDataModel xmlMetaDataModel)
    {
    }
}
