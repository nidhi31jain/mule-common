package org.mule.common.metadata;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlException;
import org.mule.common.metadata.util.XmlSchemaUtils;

import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UrlBasedSchemaProvider implements SchemaProvider {

    private List<URL> schemas;

    public UrlBasedSchemaProvider(List<URL> schemas) {
        this.schemas = schemas;
    }

    @Override
    public List<InputStream> getSchemas()  {
        List<InputStream> result = new ArrayList<InputStream>();
        for (URL schema : schemas) {
            try {
                result.add(schema.openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    @Override
    public SchemaGlobalElement findRootElement(QName rootElementName) throws XmlException {
        final SchemaTypeSystem schemaTypeLoader;
        try {
            schemaTypeLoader = XmlSchemaUtils.getSchemaTypeSystemFromUrl(schemas);
            return schemaTypeLoader.findElement(rootElementName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
