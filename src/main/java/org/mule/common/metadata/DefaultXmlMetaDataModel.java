package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;
import org.mule.common.metadata.property.xml.SchemaTypeMetaDataProperty;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import javax.xml.namespace.QName;

/**
 * <p>XML metadata representation</p>
 * <p>Shouldn't use this directly. Use {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} instead.</p>
 */
public class DefaultXmlMetaDataModel extends AbstractStructuredMetaDataModel implements XmlMetaDataModel
{

    private QName rootElement;
    private SchemaProvider schemas;
    private XmlMetaDataNamespaceManager namespaceManager;

    /**
     * @param schemas     The schemas
     * @param rootElement The root element local name
     * @param encoding    The encoding of the schemas
     * @deprecated use instead #DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, MetaDataModelProperty... properties)
     */
    @Deprecated
    public DefaultXmlMetaDataModel(List<String> schemas, String rootElement, Charset encoding)
    {
        this(schemas, new QName(rootElement), encoding, new XmlMetaDataNamespaceManager());
    }

    /**
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param encoding    The encoding of the schemas
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, MetaDataModelProperty... properties)
    {
        this(new StringBasedSchemaProvider(schemas,encoding,null), rootElement,  new XmlMetaDataFieldFactory(new StringBasedSchemaProvider(schemas,encoding,null), rootElement, new XmlMetaDataNamespaceManager()), new XmlMetaDataNamespaceManager(), properties);
    }

    /**
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param encoding    The encoding of the schemas
     * @param namespaceManager Additional manager to check namespace usage
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(new StringBasedSchemaProvider(schemas,encoding,null), rootElement,  new XmlMetaDataFieldFactory(new StringBasedSchemaProvider(schemas,encoding,null), rootElement, namespaceManager), namespaceManager, properties);
    }
    /**
     * @param schemas     The schemas
     * @param sourceUrl   The url where the relative paths will be taken from
     * @param rootElement The root element QName
     * @param encoding    The encoding of the schemas
     * @param namespaceManager Additional manager to check namespace usage
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<String> schemas,URL sourceUrl, QName rootElement, Charset encoding, XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(new StringBasedSchemaProvider(schemas,encoding,sourceUrl), rootElement,  new XmlMetaDataFieldFactory(new StringBasedSchemaProvider(schemas,encoding,sourceUrl), rootElement, namespaceManager), namespaceManager, properties);
    }

    /**
     * @param schemas     The schemas
     * @param sourceUrl   The url where the relative paths will be taken from
     * @param elementName The element QName
     * @param typeElement The type element QName
     * @param encoding    The encoding of the schemas
     * @param namespaceManager Additional manager to check namespace usage
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<String> schemas,URL sourceUrl,QName elementName ,QName typeElement, Charset encoding, XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(new StringBasedSchemaProvider(schemas,encoding,sourceUrl), elementName,  new XmlMetaDataTypeFieldFactory(new StringBasedSchemaProvider(schemas,encoding,sourceUrl), typeElement, namespaceManager), namespaceManager, properties);
    }

    /**
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param namespaceManager Additional manager to check namespace usage
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<URL> schemas, QName rootElement,  XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(new UrlBasedSchemaProvider(schemas), rootElement,  new XmlMetaDataFieldFactory(new UrlBasedSchemaProvider(schemas), rootElement, namespaceManager), namespaceManager, properties);
    }

    /**
     * @param schemas     The schemas
     * @param elementName The root element QName
     * @param typeElement The root type element QName
     * @param namespaceManager Additional manager to check namespace usage
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<URL> schemas, QName elementName, QName typeElement,  XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(new UrlBasedSchemaProvider(schemas), elementName,  new XmlMetaDataTypeFieldFactory(new UrlBasedSchemaProvider(schemas), typeElement, namespaceManager), namespaceManager, properties);
    }

    /**
     * This constructor if for internal use only
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param properties  Additional properties
     * @param fieldFactory The field factory
     * @param namespaceManager Additional manager to check namespace usage
     */
    DefaultXmlMetaDataModel(SchemaProvider schemas, QName rootElement,  XmlMetaDataFieldFactory fieldFactory, XmlMetaDataNamespaceManager namespaceManager, MetaDataModelProperty... properties)
    {
        this(schemas, rootElement, fieldFactory.createFields(), fieldFactory.isAllowsAnyFields(fieldFactory.getRootType()), namespaceManager, properties);
        if(fieldFactory.getRootType() != null)
        {
            addProperty(new SchemaTypeMetaDataProperty(fieldFactory.getRootType().getName()));
        }
    }

    /**
     * This constructor if for internal use only
     * 
     * @param schemas The schemas
     * @param rootElement The root element QName
     * @param properties Additional properties
     * @param fields The fields
     * @param allowsAnyFields see {@link StructuredMetaDataModel#isAnyFieldAllowed()}
     * @param namespaceManager Additional manager to check namespace usage
     */
    DefaultXmlMetaDataModel(SchemaProvider schemas, QName rootElement, List<MetaDataField> fields, boolean allowsAnyFields, XmlMetaDataNamespaceManager namespaceManager,
            MetaDataModelProperty... properties)
    {
        super(DataType.XML, fields, allowsAnyFields);
        this.schemas = schemas;
        this.rootElement = namespaceManager.assignPrefixIfNotPresent(rootElement);
        this.namespaceManager = namespaceManager;
        addAllProperties(properties);
    }


    @Override
    public QName getRootElement()
    {
        return rootElement;
    }

    @Override
    public List<InputStream> getSchemas()
    {
       return schemas.getSchemas();
    }

    @Override
    public void accept(MetaDataModelVisitor modelVisitor)
    {
        modelVisitor.visitXmlMetaDataModel(this);
    }

    @Override
    public String getExample()
    {
        if (hasProperty(TextBasedExampleMetaDataModelProperty.class))
        {
            return getProperty(TextBasedExampleMetaDataModelProperty.class).getExampleContent();
        }
        return null;
    }

    @Override
    public void setExample(String xmlExample)
    {
        addProperty(new TextBasedExampleMetaDataModelProperty(xmlExample));
    }
}
