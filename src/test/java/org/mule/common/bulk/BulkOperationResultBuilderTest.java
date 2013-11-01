/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.bulk;

import org.junit.Assert;
import org.junit.Test;

public class BulkOperationResultBuilderTest
{

    private final String id = "id";
    private final boolean successful = true;
    private final String statusCode = "200";
    private final String message = "hello world";
    private final String recordId = "recordId";
    private final String payload = "payload for the win!";
    private final Exception exception = new RuntimeException();
    private final String customPropertyKey1 = "customKey1";
    private final String customValue1 = "a value";
    private final String customPropertyKey2 = "customKey2";
    private final String customValue2 = "another value";

    @Test
    public void buildSuccessful()
    {

        BulkOperationResult<String> bulk = BulkOperationResult.<String> builder()
            .setId(id)
            .setSuccessful(successful)
            .addCustomProperty(customPropertyKey1, customValue1)
            .addItem(
                BulkItem.<String> builder()
                    .setStatusCode(statusCode)
                    .setMessage(message)
                    .setRecordId(recordId)
                    .setPayload(payload))
                    .addCustomProperty(customPropertyKey2, customValue2)
            .build();

        Assert.assertTrue(bulk.isSuccessful());
        Assert.assertSame(id, bulk.getId());
        Assert.assertEquals(1, bulk.getItems().size());
        Assert.assertEquals(customValue1, bulk.getCustomProperty(customPropertyKey1));

        BulkItem<String> item = bulk.getItems().get(0);
        Assert.assertTrue(item.isSuccessful());
        Assert.assertSame(statusCode, item.getStatusCode());
        Assert.assertSame(message, item.getMessage());
        Assert.assertSame(recordId, item.getId());
        Assert.assertSame(payload, item.getPayload());
        Assert.assertNull(item.getException());
        Assert.assertEquals(customValue2, bulk.getCustomProperty(customPropertyKey2));
    }

    @Test
    public void buildFailed()
    {
        BulkOperationResult<String> bulk = BulkOperationResult.<String> builder()
            .setId(id)
            .setSuccessful(successful)
            .addItem(
                BulkItem.<String> builder()
                    .setStatusCode(statusCode)
                    .setMessage(message)
                    .setRecordId(recordId)
                    .setPayload(payload)
                    .setException(exception))
            .build();

        Assert.assertFalse(bulk.isSuccessful());
        Assert.assertSame(id, bulk.getId());
        Assert.assertEquals(1, bulk.getItems().size());

        BulkItem<String> item = bulk.getItems().get(0);
        Assert.assertFalse(item.isSuccessful());
        Assert.assertSame(statusCode, item.getStatusCode());
        Assert.assertSame(message, item.getMessage());
        Assert.assertSame(recordId, item.getId());
        Assert.assertSame(payload, item.getPayload());
        Assert.assertSame(exception, item.getException());
    }

    @Test
    public void forcedOperationFail()
    {
        BulkOperationResult<String> bulk = BulkOperationResult.<String> builder()
            .setId(id)
            .setSuccessful(false)
            .addItem(
                BulkItem.<String> builder()
                    .setStatusCode(statusCode)
                    .setMessage(message)
                    .setRecordId(recordId)
                    .setPayload(payload))
            .build();

        Assert.assertFalse(bulk.isSuccessful());
        Assert.assertEquals(1, bulk.getItems().size());

        BulkItem<String> item = bulk.getItems().get(0);
        Assert.assertTrue(item.isSuccessful());
        Assert.assertNull(item.getException());
    }

    @Test
    public void forcedRecordFail()
    {
        BulkOperationResult<String> bulk = BulkOperationResult.<String> builder()
            .setId(id)
            .setSuccessful(successful)
            .addItem(
                BulkItem.<String> builder()
                    .setStatusCode(statusCode)
                    .setMessage(message)
                    .setRecordId(recordId)
                    .setSuccessful(false)
                    .setPayload(payload))
            .build();

        Assert.assertFalse(bulk.isSuccessful());
        Assert.assertEquals(1, bulk.getItems().size());

        BulkItem<String> item = bulk.getItems().get(0);
        Assert.assertFalse(item.isSuccessful());
        Assert.assertNull(item.getException());
    }

    @Test(expected = IllegalStateException.class)
    public void bulkWithNoRecords()
    {
        BulkOperationResult.<String> builder().setId(id).setSuccessful(successful).build();
    }

}
