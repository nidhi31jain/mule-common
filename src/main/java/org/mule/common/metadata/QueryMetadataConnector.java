package org.mule.common.metadata;

import org.mule.common.Result;
import org.mule.common.query.DsqlQuery;

/**
 * Interface to customize query metadata resolve mechanism
 */
public interface QueryMetadataConnector {

    Result<MetaData> getQueryMetadata(DsqlQuery dsqlQuery);
}
