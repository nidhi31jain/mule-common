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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Model for representing dynamic maps with string keys
 */

public class DefaultDefinedMapMetaDataModel extends DefaultMetaDataModel implements DefinedMapMetaDataModel<String>
{

    private Map<String, ? extends MetaDataModel> metaDataModelMap;
    private String name;

    public DefaultDefinedMapMetaDataModel(Map<String, ? extends MetaDataModel> metaDataModelMap, String name)
    {
        super(DataType.MAP);
        this.metaDataModelMap = metaDataModelMap;
        this.name = name;
    }

    public DefaultDefinedMapMetaDataModel(Map<String, ? extends MetaDataModel> metaDataModelMap)
    {
        super(DataType.MAP);
        this.metaDataModelMap = metaDataModelMap;
    }

    private static MetaDataModel getKeyMetaDataModel(Map<?, ?> map)
    {
        Class<?> baseClass = null;
        if (map != null)
        {
            for (Object key : map.keySet())
            {
                if (key == null) continue;
                else if (baseClass == null) baseClass = key.getClass();
                else
                {
                    Class<?> keyClass = key.getClass();
                    while (keyClass != null && !keyClass.isAssignableFrom(baseClass))
                    {
                        keyClass = keyClass.getSuperclass();
                    }
                    if (keyClass == null)
                    {
                        keyClass = Object.class;
                    }
                    baseClass = keyClass;
                }
            }
        }
        if (baseClass == null) baseClass = Object.class;
        return MetaDataModelFactory.getInstance().getMetaDataModel(baseClass);
    }

    private static <K> Map<K, ? extends MetaDataModel> getMetaDataModelMap(Map<K, ?> map)
    {
        Map<K, MetaDataModel> modelsMap = new HashMap<K, MetaDataModel>();
        if (map != null)
        {
            for (K key : map.keySet())
            {
                modelsMap.put(key, MetaDataModelFactory.getInstance().getMetaDataModel(map.get(key)));
            }
        }
        return modelsMap;
    }

    @Override
    public Set<String> getKeys()
    {
        return metaDataModelMap.keySet();
    }

    @Override
    public MetaDataModel getKeyMetaDataModel() {
        return new DefaultSimpleMetaDataModel(DataType.STRING);
    }

    @Override
    public MetaDataModel getValueMetaDataModel(String key) {
        return null;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((metaDataModelMap == null) ? 0 : metaDataModelMap.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultDefinedMapMetaDataModel)) return false;
        DefaultDefinedMapMetaDataModel other = (DefaultDefinedMapMetaDataModel) obj;
        if (metaDataModelMap == null)
        {
            if (other.metaDataModelMap != null) return false;
        }
        else if (!metaDataModelMap.equals(other.metaDataModelMap)) return false;
        if (name == null)
        {
            if (other.name != null) return false;
        }
        else if (!name.equals(other.name)) return false;
        return true;
    }

}


