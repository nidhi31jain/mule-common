/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common;

import org.mule.common.metadata.ConnectorMetaDataEnabled;

/**
 * Interface made for enable MetaData initializations on a mule connector when retrieving DataSense objects. This
 * interface is meant to be implemented along with {@link ConnectorMetaDataEnabled}
 */
public interface MetaDataCacheAware extends Capability
{

    /**
     * <p>Once a {@code name} is feed to a mule's connector, it will be able to initialize a cache for further uses
     * when resolving MetaData.</p>
     * <p>This is an idempotent operation, so calling it multiple times will cause the same
     * effect, initialize the cache the first time. Hence, the subsequent calls will be ignored in the connector.</p>
     *
     * @param name project name of the current project where the connector is being used. Not null.
     */
    void initializeCache(String name);

    /**
     * <p>Destroys an already created cache when invoked if the cache exists. It does nothing otherwise</p>
     *
     * @param name project name of the current project where the connector is being used. Not null.
     */
    void destroyCache(String name);
}
