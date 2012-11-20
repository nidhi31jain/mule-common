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

import java.io.PrintWriter;
import java.io.StringWriter;

public class DefaultTestResult implements TestResult
{

    private Status status;
    private String message;
    private FailureType failureType;
    private String stacktrace;

    public DefaultTestResult(TestResult.Status status)
    {
        this(status, "");
    }

    public DefaultTestResult(TestResult.Status status, String message)
    {
    	this(status, message, (Status.FAILURE.equals(status)) ? FailureType.UNSPECIFIED : null, null);
    }

    public DefaultTestResult(TestResult.Status status, String message, FailureType failureType, Throwable throwable)
    {
        this.status = status;
        this.message = message;
        this.failureType = failureType;
        if (throwable != null)
        {
	        StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw);
	        throwable.printStackTrace(pw);
	        this.stacktrace = sw.toString();
        }
        else
        {
        	this.stacktrace = null;
        }
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

	@Override
	public String getStacktrace() {
		return stacktrace;
	}

}
