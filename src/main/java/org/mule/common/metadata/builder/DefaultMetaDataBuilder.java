package org.mule.common.metadata.builder;

import org.mule.common.metadata.MetaDataModel;

import javax.xml.namespace.QName;

public class DefaultMetaDataBuilder implements MetaDataBuilder<MetaDataModel>
{

    private MetaDataBuilder<MetaDataModel> root;

    public ListMetaDataBuilder<?> createList()
    {
        final DefaultListMetaDataBuilder result = new DefaultListMetaDataBuilder(this);
        root = result;
        return result;
    }

    public PojoMetaDataBuilder<?> createPojo(Class<?> pojo)
    {
        final DefaultPojoMetaDataBuilder result = new DefaultPojoMetaDataBuilder(pojo, this);
        root = result;
        return result;
    }

    /**
     * <p>Begins building dynamic object. When its description is finished must end it with {@code endDynamicObject()} to continue the building.</p>
     * <p>Its description must be complete.</p>
     */
    public DynamicObjectBuilder<?> createDynamicObject(String name)
    {
        DefaultDynamicObjectBuilder result = new DefaultDynamicObjectBuilder(name, this);
        root = result;
        return result;
    }

    public XmlMetaDataBuilder createXmlObject(String name)
    {
        DefaultXmlMetaDataBuilder result = new DefaultXmlMetaDataBuilder(new QName(name));
        root = result;
        return result;
    }

    public JSONMetaDataBuilder createJsonObject()
    {
        JSONMetaDataBuilder result = new DefaultJsonMetaDataBuilder();
        root = result;
        return result;
    }


    public XmlMetaDataBuilder createXmlObject(QName name)
    {
        DefaultXmlMetaDataBuilder result = new DefaultXmlMetaDataBuilder(name);
        root = result;
        return result;
    }

    @Override
    public MetaDataModel build()
    {
        return root.build();
    }
}
