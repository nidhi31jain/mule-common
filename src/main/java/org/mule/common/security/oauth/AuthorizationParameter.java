/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.security.oauth;

import java.io.Serializable;

/**
 * Authorization parameter that needs to be appended to the authorize URL.
 */
public class AuthorizationParameter<T> implements Serializable
{

    private static final long serialVersionUID = 8099159357880641315L;

    private String name;
    private String description;
    private boolean optional;
    private T defaultValue;
    private Class<?> type;

    public AuthorizationParameter(String name,
                                  String description,
                                  boolean optional,
                                  T defaultValue,
                                  Class<?> type)
    {
        if (name == null || name.trim().equals(""))
        {
            throw new IllegalArgumentException("name cannot be blank");
        }

        this.name = name;
        this.description = description;
        this.optional = optional;
        this.defaultValue = defaultValue;
        this.type = type;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj)
    {
        if (obj instanceof AuthorizationParameter)
        {
            AuthorizationParameter<T> other = (AuthorizationParameter<T>) obj;
            return this.name.equals(other.getName());
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return this.name.hashCode();
    }

    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public boolean isOptional()
    {
        return optional;
    }

    public T getDefaultValue()
    {
        return defaultValue;
    }
    
    public Class<?> getType()
    {
        return type;
    }

}
