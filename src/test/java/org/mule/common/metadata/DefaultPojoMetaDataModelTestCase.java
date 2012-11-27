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

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

public class DefaultPojoMetaDataModelTestCase
{

    public interface A {}
    
    public interface B_A extends A {}
    
    public interface C_A extends A {}
    
    public interface D_BC extends B_A, C_A {}
    
    public static class ClassD implements D_BC {}
    
    public static class ClassA implements B_A {}
    
    public static class ClassE extends ClassA {}
    
    private static String classNamePrefix = "org.mule.common.metadata.DefaultPojoMetaDataModelTestCase.";
    private static String nameA = classNamePrefix + "A";
    private static String nameB = classNamePrefix + "B_A";
    private static String nameC = classNamePrefix + "C_A";
    private static String nameD = classNamePrefix + "D_BC";
    private static String nameClassA = classNamePrefix + "ClassA";
    
    @Test
    public void testParentNames()
    {
        PojoMetaDataModel pojoModel = new DefaultPojoMetaDataModel(ClassD.class);
        assertExpectedParentNames(new String[]{"java.lang.Object", nameD, nameB, nameC, nameA}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(ClassA.class);
        assertExpectedParentNames(new String[]{"java.lang.Object", nameB, nameA}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(ClassE.class);
        assertExpectedParentNames(new String[]{"java.lang.Object", nameClassA, nameB, nameA}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(Object.class);
        assertExpectedParentNames(new String[]{}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(A.class);
        assertExpectedParentNames(new String[]{}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(B_A.class);
        assertExpectedParentNames(new String[]{nameA}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(D_BC.class);
        assertExpectedParentNames(new String[]{nameA, nameB, nameC}, pojoModel);
        
        pojoModel = new DefaultPojoMetaDataModel(Boolean.class);
        assertExpectedParentNames(new String[]{"java.lang.Object", "java.io.Serializable", "java.lang.Comparable"}, pojoModel);
    }

    private void assertExpectedParentNames(String[] expectedParentNames, PojoMetaDataModel pojoModel)
    {
        Set<String> actualParentNames = pojoModel.getParents();
        assertEquals("Not the expected number of parents. ExpectedParents=" + toString(expectedParentNames) + " ActualParents=" + actualParentNames, expectedParentNames.length, actualParentNames.size());
        for (String expectedParentName : expectedParentNames)
        {
            assertTrue("Missing parent: " + expectedParentName + " from ActualParents=" + actualParentNames, actualParentNames.contains(expectedParentName));
        }
    }
    
    private static String toString(String[] array)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < array.length; i++)
        {
            sb.append(array[i]);
            if (i + 1 < array.length) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }
    
}


