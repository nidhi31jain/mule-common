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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
        switch (dataType)
        {
            case POJO:
                m = new DefaultPojoMetaDataModel(clazz);
                break;
            case LIST:
                // Because of java's type erasure, we can not know the type here so we go with Object.
                Class<?> listType = Object.class;
                m = new DefaultListMetaDataModel(getMetaDataModel(listType));
                break;
            case MAP:
                // Because of java's type erasure, we can not know the type here so we go with Object.
                Class<?> keyType = Object.class;
                Class<?> valueType = Object.class;
                m = new DefaultParameterizedMapMetaDataModel(getMetaDataModel(keyType),getMetaDataModel(valueType));
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
                m = new DefaultSimpleMetaDataModel(dataType);
                break;
        }
        
        return m;
    }

    public MetaDataField getMetaDataField(java.lang.reflect.Field f)
    {
        String name = f.getName();
        Class<?> fieldClass = f.getType();
        MetaDataModel m = null;
        DataType dataType = factory.getDataType(fieldClass);
        switch (dataType)
        {
            case POJO:
                m = new DefaultPojoMetaDataModel(fieldClass);
                break;
            case LIST:
                Class<?> elementClass = Object.class;
                
                Type t = f.getGenericType();
                if (t instanceof ParameterizedType)
                {
                    Type elementType = ((ParameterizedType)t).getActualTypeArguments()[0];
                    if (elementType instanceof Class)
                    {
                        elementClass = (Class<?>) elementType;
                    }
                }
                m = new DefaultListMetaDataModel(getMetaDataModel(elementClass));
                break;
            case MAP:
                Class<?> keyClass = Object.class;
                Class<?> valueClass = Object.class;

                t = f.getGenericType();
                if (t instanceof ParameterizedType)
                {
                    Type[] elementTypes = ((ParameterizedType)t).getActualTypeArguments();
                    if (elementTypes.length == 2)
                    {
                        if (elementTypes[0] instanceof Class)
                        {
                            keyClass = (Class<?>) elementTypes[0];
                        }
                        if (elementTypes[1] instanceof Class)
                        {
                            valueClass = (Class<?>) elementTypes[1];
                        }
                    }
                }
                m = new DefaultParameterizedMapMetaDataModel(getMetaDataModel(keyClass), getMetaDataModel(valueClass));
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
                m = new DefaultSimpleMetaDataModel(dataType);
                break;
        }

        return new DefaultMetaDataField(name, m);
    }

    public List<MetaDataField> getFieldsForClass(Class<?> clazz)
    {
        //Todo change this for Introspector of beans
        List<MetaDataField> fields = new ArrayList<MetaDataField>();
        for (java.lang.reflect.Field f : getInheritedPrivateFields(clazz))
        {
            fields.add(getMetaDataField(f));
        }
        return fields;
    }

    public static List<Field> getInheritedPrivateFields(Class<?> type) {
        List<Field> result = new ArrayList<Field>();

        Class<?> i = type;
        while (i != null && i != Object.class) {
            for (Field field : i.getDeclaredFields()) {
                if (!field.isSynthetic() && !Modifier.isStatic(field.getModifiers())) {
                    result.add(field);
                }
            }
            i = i.getSuperclass();
        }

        return result;
    }

    public Set<String> getParentNames(Class<?> clazz)
    {
        Set<String> parents = new HashSet<String>();
        for (Class<?> c : clazz.getInterfaces())
        {
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


