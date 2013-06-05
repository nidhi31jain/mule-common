/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.security.oauth.exception;

/**
 * Exception thrown when the request token needed for building the authorization URL
 * cannot be acquired
 */
public class UnableToAcquireRequestTokenException extends Exception
{

    private static final long serialVersionUID = 7270023278136600114L;

    public UnableToAcquireRequestTokenException(Throwable throwable)
    {
        super(throwable);
    }

}
