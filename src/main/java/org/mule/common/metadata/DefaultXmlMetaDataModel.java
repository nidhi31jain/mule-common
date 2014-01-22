package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;
import org.mule.common.metadata.property.TextBasedExampleMetaDataModelProperty;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

public class DefaultXmlMetaDataModel extends AbstractMetaDataModel implements XmlMetaDataModel
{

    private List<String> schemas;
    private QName rootElement;
    private Charset encoding;


    public DefaultXmlMetaDataModel(List<String> schemas, String rootElement, Charset encoding)
    {
        this(schemas, new QName(rootElement), encoding);
    }

    public DefaultXmlMetaDataModel(List<String> schemas, QName rootElement, Charset encoding, MetaDataModelProperty... properties)
    {
        super(DataType.XML);
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
