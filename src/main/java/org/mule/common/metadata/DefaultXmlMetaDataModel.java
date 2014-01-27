package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

public class DefaultXmlMetaDataModel extends AbstractStructuredMetaDataModel implements XmlMetaDataModel
{

    private List<String> schemas;
    private QName rootElement;
    private Charset encoding;


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
        this(schemas, rootElement, encoding, new XmlMetaDataFieldFactory(schemas, rootElement, encoding).createFields(), properties);
    }

    /**
     * This constructor if for internal use only
     * @param schemas     The schemas
     * @param rootElement The root element QName
     * @param encoding    The encoding of the schemas
     * @param properties  Additional properties
     * @param fields The fields
     */
    DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, List<MetaDataField> fields, MetaDataModelProperty... properties)
    {
        super(DataType.XML, fields);
        this.schemas = schemas;
        this.rootElement = rootElement;
        this.encoding = encoding;
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
        List<InputStream> result = new ArrayList<InputStream>();
        for (String schema : schemas)
        {
            result.add(new ByteArrayInputStream(schema.getBytes(encoding)));
        }
        return result;
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
