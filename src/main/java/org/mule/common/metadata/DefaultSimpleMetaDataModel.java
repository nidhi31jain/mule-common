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

import org.mule.common.metadata.datatype.DataType;

import java.util.Set;

public class DefaultSimpleMetaDataModel extends DefaultMetaDataModel implements SimpleMetaDataModel
{

    private String name;
    private Set<String> parentNames;

    public DefaultSimpleMetaDataModel(DataType dataType, String name, Set<String> parentNames)
    {
        super(dataType);
        this.name = name;
        this.parentNames = parentNames;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Set<String> getParents()
    {
        return parentNames;
    }

    @Override
    public String toString()
    {
        return "DefaultSimpleMetaDataModel:{ name:" + name + " dataType:" + getDataType().toString() + " parentNames:" + toString(parentNames) + " }";
    }
    
    private static final String toString(Set<String> strings)
    {
        if (strings == null)
        {
            return "null";
        }
        else
        {
            StringBuilder sb = new StringBuilder("{ ");
            for (String s : strings)
            {
                sb.append(s);
                sb.append(",");
            }
            sb.append(" }");
            return sb.toString();
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((parentNames == null) ? 0 : parentNames.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultSimpleMetaDataModel)) return false;
        DefaultSimpleMetaDataModel other = (DefaultSimpleMetaDataModel) obj;
        if (name == null)
        {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        if (parentNames == null)
        {
            if (other.parentNames != null) return false;
        }
        else if (!parentNames.equals(other.parentNames)) return false;
        return true;
    }
    
}


