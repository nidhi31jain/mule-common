/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common;

import org.mule.common.connectiontest.ConnectionTester;
import org.mule.common.metadata.MetadataLocator;

import java.util.ServiceLoader;

public class CommonServiceLocator
{

    public static ConnectionTester getConnectionTest()
    {
        return ServiceLoader.load(ConnectionTester.class).iterator().next();
    }

    public static MetadataLocator getMetadataLocator()
    {
        return ServiceLoader.load(MetadataLocator.class).iterator().next();
    }

}
