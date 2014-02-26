package org.mule.common.metadata.builder;

import org.mule.common.metadata.MetaDataModel;

import javax.xml.namespace.QName;

/**
 * This is a builder created for easily describe metadata types from Devkit.
 * From this object you can create models for Lists, Pojos, Dynamic Objects and Xml Objects.
 * <br/>
 * <br/>
 * Following there is an example usage of it for describing a <strong>Pojo Class</strong>:
 * <pre>
 * {@code MetaDataModel authorModel = new DefaultMetaDataBuilder().createPojo(Author.class).build();}
 * </pre>
 * An example for <strong>Pojo List</strong>:
 * <pre>
 * {@code MetaDataModel bookListModel = new DefaultMetaDataBuilder().createList().ofPojo(Book.class).build();}
 * </pre>
 * And an example for <strong>Dynamic Objects</strong>:
 * <pre>
 * {@code MetaDataModel bookModel = new DefaultMetaDataBuilder().createDynamicObject("Book")
 *                                                      .addSimpleField("title",DataType.STRING)
 *                                                      .addSimpleField("synopsis",DataType.STRING)
 *                                                      .addDynamicObjectField("author")
 *                                                          .addSimpleField("firstName",DataType.STRING)
 *                                                          .addSimpleField("lastName",DataType.STRING)
 *                                                      .endDynamicObject()
 *                                                      .build();
 *     }
 * </pre>
 *
 */
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
