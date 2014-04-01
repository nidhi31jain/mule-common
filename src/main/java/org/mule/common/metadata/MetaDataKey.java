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

import org.mule.common.metadata.key.property.MetaDataKeyProperty;

import java.util.List;

/**
 * <p>This represent a service entity/type. The <strong>Id</strong> is the XML identifier name for the entity,
 * <strong>Display Name</strong> is used for Studio UI for improve the user experience, and <strong>Category</strong>
 * (despite not being shown by Studio UI) is mandatory for grouping types.
 * Properties are used for advanced scenarios like grouping entities/types or <strong>DSQL</strong>.</p>
 * <p>Shouldn't implement this interface as it can change and break compatibility.
 * Instead please use the {@link DefaultMetaDataKey} implementation.</p>
 */
public interface MetaDataKey
	extends Comparable<MetaDataKey>
{
    public String getId();
    public String getDisplayName();
    public String getCategory();

    List<MetaDataKeyProperty> getProperties();

    boolean addProperty(MetaDataKeyProperty metaDataKeyProperty);

    boolean removeProperty(MetaDataKeyProperty metaDataKeyProperty);

    boolean hasProperty(Class<? extends MetaDataKeyProperty> metaDataKeyProperty);

    <T extends MetaDataKeyProperty> T getProperty(Class<T> metaDataKeyProperty);
}


