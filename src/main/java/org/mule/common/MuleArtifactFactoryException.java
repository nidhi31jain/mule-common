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

public class MuleArtifactFactoryException extends Exception
{

    private static final long serialVersionUID = 2962452640497689015L;

    public MuleArtifactFactoryException(String message)
    {
        super(message);
    }

    public MuleArtifactFactoryException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
