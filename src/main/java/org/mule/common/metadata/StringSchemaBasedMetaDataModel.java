/**
 *
 */
package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class StringSchemaBasedMetaDataModel extends AbstractMetaDataModel implements SchemaBasedMetaDataModel
{


    private List<String> schemas;
    private String rootElement;
    private Charset encoding;

    public StringSchemaBasedMetaDataModel(DataType dataType, List<String> schemas, String rootElement, Charset encoding)
    {
        super(dataType);
        this.schemas = schemas;
        this.rootElement = rootElement;
        this.encoding = encoding;
    }

    public String getRootElement()
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
    }
}
