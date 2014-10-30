package org.mule.common.metadata.util;

import org.mule.common.metadata.XmlMetaDataFieldFactory;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.impl.schema.BuiltinSchemaTypeSystem;
import org.apache.xmlbeans.impl.schema.SchemaTypeLoaderImpl;
import org.apache.xmlbeans.impl.schema.SchemaTypeSystemCompiler;


public class XmlSchemaUtils {

    public static SchemaTypeSystem getSchemaTypeSystem(List<String> schemas) throws XmlException {
        final XmlOptions options = new XmlOptions();
        options.setCompileNoUpaRule();
        options.setCompileNoValidation();
        options.setCompileDownloadUrls();

        /* Load the schema */
        final XmlObject[] schemaRepresentation = new XmlObject[schemas.size()];
        final SchemaTypeLoader contextTypeLoader = SchemaTypeLoaderImpl.build(new SchemaTypeLoader[]{BuiltinSchemaTypeSystem.get()}, null, XmlMetaDataFieldFactory.class.getClassLoader());
        for (int i = 0; i < schemas.size(); i++) {
            schemaRepresentation[i] = contextTypeLoader.parse(schemas.get(i), null, null);
        }
        return SchemaTypeSystemCompiler.compile(null, null, schemaRepresentation, null, contextTypeLoader, null, options);
    }


    public static SchemaTypeSystem getSchemaTypeSystemFromUrl(List<URL> schemas) throws XmlException, IOException {
        final XmlOptions options = new XmlOptions();
        options.setCompileNoUpaRule();
        options.setCompileNoValidation();
        options.setCompileDownloadUrls();

        /* Load the schema */
        final XmlObject[] schemaRepresentation = new XmlObject[schemas.size()];
        final SchemaTypeLoader contextTypeLoader = SchemaTypeLoaderImpl.build(new SchemaTypeLoader[]{BuiltinSchemaTypeSystem.get()}, null,
                XmlMetaDataFieldFactory.class.getClassLoader());

        int i = 0;
        for (URL schemaURL : schemas) {
            XmlObject schemaObject = contextTypeLoader.parse(schemaURL, null, null);
            schemaRepresentation[i] = schemaObject;
            i++;
        }
        return SchemaTypeSystemCompiler.compile(null, null, schemaRepresentation, null, contextTypeLoader, null, options);
    }
}
