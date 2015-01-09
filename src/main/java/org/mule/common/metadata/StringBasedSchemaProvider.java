package org.mule.common.metadata;

import org.mule.common.metadata.util.XmlSchemaUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlException;

public class StringBasedSchemaProvider implements SchemaProvider
{

    private List<String> schemas;
    private Charset encoding;
    private URL baseUri;

    public StringBasedSchemaProvider(List<String> schemas, Charset encoding, URL baseUri)
    {
        this.schemas = schemas;
        this.encoding = encoding;
        this.baseUri = baseUri;
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
    public SchemaGlobalElement findRootElement(QName rootElementName) throws XmlException
    {
        final SchemaTypeSystem schemaTypeLoader = XmlSchemaUtils.getSchemaTypeSystem(schemas, baseUri);
        return schemaTypeLoader.findElement(rootElementName);
    }

    @Override
    public SchemaType findRootType(QName rootElementName) throws XmlException
    {
        final SchemaTypeSystem schemaTypeLoader = XmlSchemaUtils.getSchemaTypeSystem(schemas, baseUri);
        return schemaTypeLoader.findType(rootElementName);
    }
}
