package org.mule.common.metadata;

import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlException;
import org.mule.common.metadata.util.XmlSchemaUtils;

import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StringBasedSchemaProvider implements SchemaProvider {

    private List<String> schemas;
    private Charset encoding;

    public StringBasedSchemaProvider(List<String> schemas, Charset encoding) {
        this.schemas = schemas;
        this.encoding = encoding;
    }

    @Override
    public List<InputStream> getSchemas() {
        List<InputStream> result = new ArrayList<InputStream>();
        for (String schema : schemas) {
            result.add(new ByteArrayInputStream(schema.getBytes(encoding)));
        }
        return result;
    }

    @Override
    public SchemaGlobalElement findRootElement(QName rootElementName) throws XmlException {
        final SchemaTypeSystem schemaTypeLoader = XmlSchemaUtils.getSchemaTypeSystem(schemas);
        return schemaTypeLoader.findElement(rootElementName);
    }
}
