package org.mule.common.metadata;

import java.io.InputStream;
import java.util.List;

/**
 *
 */
public interface SchemaBasedMetaDataModel extends MetaDataModel
{
    List<InputStream> getSchemas();

    String getRootElement();
}
