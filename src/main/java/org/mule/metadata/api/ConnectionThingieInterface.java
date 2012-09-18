/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.metadata.api;

import org.mule.metadata.api.Testable.TestResult;

import javax.xml.namespace.QName;

public interface ConnectionThingieInterface
{

    boolean supportsConnectionTest(QName element);

    boolean hasMetaData(QName element);

    TestResult testConnection(MuleXmlElement element, ModelAccessCallback callback);

    MetaData getMetaData(MuleXmlElement element, ModelAccessCallback callback);

}
