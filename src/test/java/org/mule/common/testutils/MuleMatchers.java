/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.common.testutils;


import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Set of matchers extending Hamcrest core set
 */
public final class MuleMatchers {


    public static Matcher<Object> isExactlyA(final Class<?> type) {
        return new BaseMatcher<Object>() {
            @Override
            public boolean matches(final Object item) {
                return type.equals(item.getClass());
            }
            @Override
            public void describeTo(final Description description) {
                description.appendText("value should be an instance of ").appendValue(type.toString());
            }

            @Override
            public void describeMismatch(Object item, Description description) {
                if (item == null) {
                    description.appendText("was null");
                }
                else {
                    description.appendText("was an instance of ").appendValue(item.getClass().toString());
                }
            }
        };
    }
}
