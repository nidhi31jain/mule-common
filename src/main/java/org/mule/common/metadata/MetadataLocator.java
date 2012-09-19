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

import org.mule.common.config.XmlDocumentAccessor;
import org.mule.common.config.XmlElement;

import javax.xml.namespace.QName;

public interface MetadataLocator
{

    boolean hasMetaData(QName element);

    MetaData getMetaData(XmlElement element, XmlDocumentAccessor callback);

}
