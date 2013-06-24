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
 * This exception signals that a connector's authorization process hasn't taken place
 */
public class NotAuthorizedException extends Exception
{

    private static final long serialVersionUID = -3762818759852803099L;

    private String accessTokenId;

    public NotAuthorizedException(String message)
    {
        super(message);
    }

    public NotAuthorizedException(String message, String accessTokenId)
    {
        this(message);
        this.accessTokenId = accessTokenId;
    }

    public NotAuthorizedException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public NotAuthorizedException(String message, String accessTokenId, Throwable throwable)
    {
        this(message, throwable);
        this.accessTokenId = accessTokenId;
    }

    public String getAccessTokenId()
    {
        return accessTokenId;
    }
}
