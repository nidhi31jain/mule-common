package org.mule.common.metadata;

import org.mule.common.Result;
import org.mule.common.query.Query;

/**
 * Interface to customize query metadata resolve mechanism
 */
public interface QueryMetadataConnector {

    Result<MetaData> getQueryMetadata(Query query);
}
