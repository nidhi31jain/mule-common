/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.metadata;

import org.mule.common.metadata.field.property.MetaDataFieldProperty;

/**
 * <p>Intermediate layer between metadata and {@link MetaDataModel}. </p>
 * <p>The recommended approach for building it is to use the implemented {@link DefaultMetaData} constructor with the {@link org.mule.common.metadata.builder.DefaultMetaDataBuilder} for the {@code MetaDataModel} building.</p>
 */
public interface MetaData
{

    /**
     * Returns the contained MetaDataModel
     */
    MetaDataModel getPayload();

    /**
     * Returns the properties contained. Shouldn't be used except for advanced scenarios.
     */
    MetaDataProperties getProperties(MetaDataPropertyScope scope);

    /**
     * Adds one property. Shouldn't be used except for advanced scenarios.
     */
    void addProperty(MetaDataPropertyScope scope, String name, MetaDataModel propertyModel, MetaDataFieldProperty... properties);

    /**
     * Removes one propery. Shouldn't be used except for advanced scenarios.
     */
    void removeProperty(MetaDataPropertyScope scope, String name);
}
