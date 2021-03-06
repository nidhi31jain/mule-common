/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.bulk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBulkBuilder
{

    private Map<String, Serializable> customProperties = null;

    protected void customProperty(String key, Serializable value)
    {
        if (this.customProperties == null)
        {
            this.customProperties = new HashMap<String, Serializable>();
        }

        this.customProperties.put(key, value);
    }
    
    protected Map<String, Serializable> getCustomProperties()
    {
        return customProperties;
    }
}
