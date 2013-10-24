/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.bulk;

import org.mule.common.bulk.BulkItem.BulkItemBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to provide item level information about a bulk operation.
 * This master entity represents the bulk operation as a whole, while the detail
 * entity {@link BulkItem} represents the operation status for each individual data
 * piece
 */
public final class BulkOperationResult<T> implements Serializable
{

    private static final long serialVersionUID = 8039267004891928585L;

    /**
     * The operation id
     */
    private final Serializable id;

    /**
     * Whether or not the operation was successful. Should be <code>true</code> if
     * and only if all the child {@link BulkItem} entities were also successful
     */
    private final boolean successful;

    /**
     * A sorted list of {@link BulkItem}, one per each item in the original
     * operation, no matter if the record was successful or not
     */
    private final List<BulkItem<T>> items;
    
    private BulkOperationResult(Serializable id, boolean successful, List<BulkItem<T>> items)
    {
        this.id = id;
        this.successful = successful;
        this.items = items;
    }

    public Serializable getId()
    {
        return id;
    }

    public boolean isSuccessful()
    {
        return successful;
    }

    public List<BulkItem<T>> getItems()
    {
        return items;
    }

    public static <T> BulkOperationResultBuilder<T> builder()
    {
        return new BulkOperationResultBuilder<T>();
    }

    public static class BulkOperationResultBuilder<T>
    {

        private Serializable id;
        private boolean successful = true;
        private List<BulkItemBuilder<T>> items = new ArrayList<BulkItemBuilder<T>>();
        
        private BulkOperationResultBuilder()
        {
        }

        public BulkOperationResultBuilder<T> setSuccessful(boolean successful)
        {
            this.successful = successful;
            return this;
        }

        public BulkOperationResultBuilder<T> setId(Serializable id)
        {
            this.id = id;
            return this;
        }

        public BulkOperationResultBuilder<T> addItem(BulkItemBuilder<T> recordResultBuilder)
        {
            this.items.add(recordResultBuilder);
            return this;
        }

        public BulkOperationResult<T> build()
        {
            if (this.items.isEmpty())
            {
                throw new IllegalStateException(
                    "A BulkOperationResult must have at least one BulkItem. Please add a result an try again");
            }

            List<BulkItem<T>> items = new ArrayList<BulkItem<T>>(this.items.size());
            for (BulkItemBuilder<T> recordBuilder : this.items)
            {
                BulkItem<T> record = recordBuilder.build();
                items.add(record);
                if (!record.isSuccessful())
                {
                    this.successful = false;
                }
            }

            return new BulkOperationResult<T>(id, successful, items);
        }

    }

}
