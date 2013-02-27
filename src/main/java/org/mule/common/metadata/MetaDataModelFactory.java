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
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
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
        return getMetaDataModel(null, clazz);
    }
    
    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> MetaDataModel getMetaDataModel(T obj, Class<T> clazz)
    {
        MetaDataModel m = null;
        DataType dataType = factory.getDataType(clazz);
        switch (dataType)
        {
            case POJO:
                m = new DefaultPojoMetaDataModel(clazz);
                break;
            case LIST:
                TypeVariable<Class<T>>[] listTypeParameters = clazz.getTypeParameters();
                Class listType;
                if(listTypeParameters.length == 1){
                    listType = getClassOfTypeParameter(listTypeParameters[0]);
                }else{
                    listType = Object.class;
                }

                m = new DefaultListMetaDataModel(getMetaDataModel(listType));
                break;
            case MAP:
                if (obj != null && obj instanceof Map && !((Map<?,?>)obj).isEmpty())
                {
                    Map<String,? extends MetaDataModel> map = (Map<String,? extends MetaDataModel>) obj;
                    m = new DefaultDefinedMapMetaDataModel(map);
                }
                else
                {
                    TypeVariable<Class<T>>[] mapTypeParameters = clazz.getTypeParameters();
                    Class keyType;
                    Class valueType;
                    if(mapTypeParameters.length == 2){

                        keyType = getClassOfTypeParameter(mapTypeParameters[0]);
                        valueType = getClassOfTypeParameter(mapTypeParameters[1]);
                    }else{
                        keyType = Object.class;
                        valueType = Object.class;
                    }
                    m = new DefaultParameterizedMapMetaDataModel(getMetaDataModel(keyType),getMetaDataModel(valueType));
                }
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

    private <T> Class getClassOfTypeParameter(TypeVariable<Class<T>> typeParameter) {
        Class listType;
        if(typeParameter != null){
            listType = typeParameter.getGenericDeclaration();
        }else {
            listType = Object.class;
        }
        return listType;
    }

    public MetaDataModel getMetaDataModel(java.lang.reflect.Field f)
    {
        String name = f.getName();
        Class<?> fieldClass = f.getType();
        Set<String> parentNames = getParentNames(fieldClass);
        SimpleMetaDataModel m = null;
        DataType dataType = factory.getDataType(fieldClass);
        switch (dataType)
        {
            case POJO:
                m = new DefaultPojoMetaDataModel(fieldClass, name);
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
                m = new DefaultSimpleListMetaDataModel(getMetaDataModel(elementClass), name, parentNames);
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
                m = new DefaultSimpleParameterizedMapMetaDataModel(getMetaDataModel(keyClass), getMetaDataModel(valueClass), name, parentNames);
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

    public List<MetaDataModel> getFieldsForClass(Class<?> clazz)
    {

        //Todo change this for Introspector of beans
        List<MetaDataModel> fields = new ArrayList<MetaDataModel>();
        for (java.lang.reflect.Field f : getInheritedPrivateFields(clazz))
        {
            fields.add(getMetaDataModel(f));
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
    
    private static class DefaultSimpleParameterizedMapMetaDataModel extends DefaultParameterizedMapMetaDataModel implements SimpleMetaDataModel
    {
        private String name;
        private Set<String> parents;
      
        public DefaultSimpleParameterizedMapMetaDataModel(MetaDataModel keyMetaDataModel, MetaDataModel valueMetaDataModel, String name, Set<String> parents)
        {
            super(keyMetaDataModel, valueMetaDataModel);
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


