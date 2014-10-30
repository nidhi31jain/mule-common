package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

/**
 * <p>XML metadata representation</p>
 * <p>Shouldn't use this directly. Use {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} instead.</p>
 */
public class DefaultXmlMetaDataModel extends AbstractStructuredMetaDataModel implements XmlMetaDataModel
{

    private SchemaProvider schemas;
    private QName rootElement;

    /**
     * @param schemas     The schemas
     * @param rootElement The root element local name
     * @param encoding    The encoding of the schemas
     * @deprecated use instead #DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, MetaDataModelProperty... properties)
     */
    @Deprecated
    public DefaultXmlMetaDataModel(List<String> schemas, String rootElement, Charset encoding)
    {
        this(schemas, new QName(rootElement), encoding);
    }

    /**
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param encoding    The encoding of the schemas
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, MetaDataModelProperty... properties)
    {
        this(new StringBasedSchemaProvider(schemas,encoding), rootElement,  new XmlMetaDataFieldFactory(new StringBasedSchemaProvider(schemas,encoding), rootElement).createFields(), properties);
    }

    /**
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param properties  Additional properties
     */
    public DefaultXmlMetaDataModel(List<URL> schemas, QName rootElement,  MetaDataModelProperty... properties)
    {
        this(new UrlBasedSchemaProvider(schemas), rootElement,  new XmlMetaDataFieldFactory(new UrlBasedSchemaProvider(schemas), rootElement).createFields(), properties);
    }

    /**
     * This constructor if for internal use only
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param properties  Additional properties
     * @param fields The fields
     */
    DefaultXmlMetaDataModel(SchemaProvider schemas, QName rootElement,  List<MetaDataField> fields, MetaDataModelProperty... properties)
    {
        super(DataType.XML, fields);
        this.schemas = schemas;
        this.rootElement = rootElement;
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
