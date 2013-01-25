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
import org.mule.common.metadata.datatype.DataTypeFactory;

import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MetaDataModelFactory
{
    private static MetaDataModelFactory instance = new MetaDataModelFactory();
    
    private DataTypeFactory factory;
    
    private MetaDataModelFactory()
    {
        factory = DataTypeFactory.getInstance();
    }
    
    public static MetaDataModelFactory getInstance()
    {
        return instance;
    }
    
    public <T> MetaDataModel getMetaDataModel(Class<T> clazz)
    {
        MetaDataModel m = null;
        DataType dataType = factory.getDataType(clazz);
        switch (dataType) {
            case POJO:
                m = new DefaultPojoMetaDataModel(clazz);
                break;
            case LIST:
                m = new DefaultListMetaDataModel(new DefaultMetaDataModel(DataType.POJO));
                break;
            case MAP:
                m = new DefaultMapMetaDataModel<Object>(new DefaultMetaDataModel(DataType.POJO), new HashMap<Object, MetaDataModel>());
                break;
            case VOID:
            case BOOLEAN:
            case NUMBER:
            case STRING:
            case BYTE_ARRAY:
            case STREAM:
            case ENUM: 
            case DATE_TIME:
            default:
                m = new DefaultMetaDataModel(dataType);
                break;
        }
        
        return m;
    }
    
    public SimpleMetaDataModel getMetaDataModel(java.lang.reflect.Field f)
    {
        String name = f.getName();
        Class<?> fieldClass = f.getType();
        Set<String> parentNames = getParentNames(fieldClass);
        SimpleMetaDataModel m = null;
        DataType dataType = factory.getDataType(fieldClass);
        switch (dataType) {
            case POJO:
                m = new DefaultPojoMetaDataModel(fieldClass, name);
                break;
            case LIST:
                Class<?> elementClass = Object.class;
                
                Type t = f.getGenericType();
                if (t instanceof ParameterizedType) {
                    Type elementType = ((ParameterizedType)t).getActualTypeArguments()[0];
                    if (elementType instanceof Class) {
                        elementClass = (Class<?>) elementType;
                    }
                }
                m = new DefaultSimpleListMetaDataModel(getMetaDataModel(elementClass), name, parentNames);
                break;
            case MAP:
                Class<?> keyClass = Object.class;

                t = f.getGenericType();
                if (t instanceof ParameterizedType) {
                    Type[] elementTypes = ((ParameterizedType)t).getActualTypeArguments();
                    if (elementTypes.length == 2) {
                        if (elementTypes[0] instanceof Class) {
                            keyClass = (Class<?>) elementTypes[0];
                        }
                    }
                }
                m = new DefaultSimpleMapMetaDataModel<Object>(getMetaDataModel(keyClass), new HashMap<Object, MetaDataModel>(), name, parentNames);
                break;
            case VOID:
            case BOOLEAN:
            case NUMBER:
            case STRING:
            case BYTE_ARRAY:
            case STREAM:
            case ENUM: 
            case DATE_TIME:
            default:
                m = new DefaultSimpleMetaDataModel(dataType, name, parentNames);
                break;
        }
        return m;
    }

    public List<SimpleMetaDataModel> getFieldsForClass(Class<?> clazz) {
        List<SimpleMetaDataModel> fields = new ArrayList<SimpleMetaDataModel>();
        for (java.lang.reflect.Field f : clazz.getDeclaredFields()) {
            if (!Modifier.isStatic(f.getModifiers())) {
                fields.add(getMetaDataModel(f));
            }
        }
        return fields;
    }
    
    private static class DefaultSimpleListMetaDataModel extends DefaultListMetaDataModel implements SimpleMetaDataModel
    {
        private String name;
        private Set<String> parents;
        
        public DefaultSimpleListMetaDataModel(MetaDataModel elementMetaDataModel, String name, Set<String> parents)
        {
            super(elementMetaDataModel);
            this.name = name;
            this.parents = parents;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public Set<String> getParents()
        {
            return parents;
        }
    }
    
    private static class DefaultSimpleMapMetaDataModel<K> extends DefaultMapMetaDataModel<K> implements SimpleMetaDataModel
    {
        private String name;
        private Set<String> parents;
        
        public DefaultSimpleMapMetaDataModel(MetaDataModel keyMetaDataModel, Map<K, ? extends MetaDataModel> metaDataModelMap, String name, Set<String> parents)
        {
            super(keyMetaDataModel, metaDataModelMap);
            this.name = name;
            this.parents = parents;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public Set<String> getParents()
        {
            return parents;
        }
    }

    public Set<String> getParentNames(Class<?> clazz)
    {
        Set<String> parents = new HashSet<String>();
        for (Class<?> c : clazz.getInterfaces()) {
            if (c != null)
            {
                parents.add(c.getCanonicalName());
                parents.addAll(getParentNames(c));
            }
        }
        Class<?> parent = clazz.getSuperclass();
        if (parent != null)
        {
            parents.add(parent.getCanonicalName());
            parents.addAll(getParentNames(parent));
        }
        return parents;
    }
}


