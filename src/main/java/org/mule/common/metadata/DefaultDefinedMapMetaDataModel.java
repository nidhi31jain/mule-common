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

public class DefaultDefinedMapMetaDataModel<K> extends DefaultMetaDataModel implements DefinedMapMetaDataModel<K>
{

    private MetaDataModel keyMetaDataModel;
    private Map<K, ? extends MetaDataModel> metaDataModelMap;
    
    public DefaultDefinedMapMetaDataModel(Map<K,?> map)
    {
        this(getKeyMetaDataModel(map), getMetaDataModelMap(map));
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

    public DefaultDefinedMapMetaDataModel(MetaDataModel keyMetaDataModel, Map<K, ? extends MetaDataModel> metaDataModelMap)
    {
        super(DataType.MAP);
        this.keyMetaDataModel = keyMetaDataModel;
        this.metaDataModelMap = metaDataModelMap;
    }

    @Override
    public Set<K> getKeys()
    {
        return metaDataModelMap.keySet();
    }

    @Override
    public MetaDataModel getKeyMetaDataModel()
    {
        return keyMetaDataModel;
    }

    @Override
    public MetaDataModel getValueMetaDataModel(K key)
    {
        return metaDataModelMap.get(key);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((keyMetaDataModel == null) ? 0 : keyMetaDataModel.hashCode());
        result = prime * result + ((metaDataModelMap == null) ? 0 : metaDataModelMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultDefinedMapMetaDataModel)) return false;
        DefaultDefinedMapMetaDataModel<?> other = (DefaultDefinedMapMetaDataModel<?>) obj;
        if (keyMetaDataModel == null)
        {
            if (other.keyMetaDataModel != null) return false;
        }
        else if (!keyMetaDataModel.equals(other.keyMetaDataModel)) return false;
        if (metaDataModelMap == null)
        {
            if (other.metaDataModelMap != null) return false;
        }
        else if (!metaDataModelMap.equals(other.metaDataModelMap)) return false;
        return true;
    }

}


