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

public class DefaultTestResult implements TestResult
{

    private Status status;
    private String message;
    private FailureType failureType;

    public DefaultTestResult(TestResult.Status status)
    {
        this(status, "");
    }

    public DefaultTestResult(TestResult.Status status, String message)
    {
    	this(status, message, (Status.FAILURE.equals(status)) ? FailureType.UNSPECIFIED : null);
    }

    public DefaultTestResult(TestResult.Status status, String message, FailureType failureType)
    {
        this.status = status;
        this.message = message;
        this.failureType = failureType;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    @Override
    public Status getStatus()
    {
        return status;
    }

	@Override
	public FailureType getFailureType() {
		return failureType;
	}

}
