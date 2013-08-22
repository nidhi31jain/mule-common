package org.mule.common.metadata;

import org.mule.common.Capability;
import org.mule.common.Result;
import org.mule.common.query.DsqlQuery;

/**
 * Used to translate a model query to a native one in an string format
 */
public interface NativeQueryMetadataTranslator extends Capability{

    Result<String> toNativeQuery(DsqlQuery dsqlQuery);
}
