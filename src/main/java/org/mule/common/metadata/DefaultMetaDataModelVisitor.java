/**
 *
 */
package org.mule.common.metadata;

/**
 * Default visitor for go over {@link MetaDataModel} structure.
 */
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

    @Override
    public void visitUnknownMetaDataModel(UnknownMetaDataModel unknownMetaDataModel)
    {

    }
}
