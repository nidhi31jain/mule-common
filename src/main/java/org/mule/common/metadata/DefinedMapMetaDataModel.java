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

import java.util.Set;

/**
 * Represents a map with defined string keys.
 */
public interface DefinedMapMetaDataModel extends StructuredMetaDataModel
{

     String getName();

     Set<String> getKeys();

     MetaDataModel getKeyMetaDataModel();

     MetaDataModel getValueMetaDataModel(String key);

}