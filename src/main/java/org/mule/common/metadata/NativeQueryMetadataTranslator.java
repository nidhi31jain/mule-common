package org.mule.common.metadata;

import org.mule.common.Result;
import org.mule.common.query.Query;

/**
 * Used to translate a model query to a native one in an string format
 */
public interface NativeQueryMetadataTranslator{

    Result<String> toNativeQuery(Query query);
}
