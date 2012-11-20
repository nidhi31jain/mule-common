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

public interface TestResult
{
    Status getStatus();

    String getMessage();
    
    FailureType getFailureType();

    static enum Status
    {
        SUCCESS, FAILURE
    }
    
    static enum FailureType
    {
    	INVALID_CONFIGURATION,
    	INVALID_CREDENTIALS,
    	NOT_AUTHORIZED,
    	UNKNOWN_HOST,
    	CONNECTION_FAILURE,
    	RESOURCE_UNAVAILABLE,
    	UNSPECIFIED
    }
}
