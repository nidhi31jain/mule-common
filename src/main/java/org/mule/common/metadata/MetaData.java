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

public interface MetaData
{

    MetaDataModel getPayload();

    MetaDataProperties getProperties(MetaDataPropertyScope scope);

    void addProperty(MetaDataPropertyScope scope, String name, MetaDataModel propertyModel, MetaDataFieldProperty... properties);

    void removeProperty(MetaDataPropertyScope scope, String name);
}
