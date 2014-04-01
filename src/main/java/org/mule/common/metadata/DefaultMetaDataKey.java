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

import org.mule.common.metadata.key.property.MetaDataKeyProperty;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>{@link MetaDataKey} default implementation. This should be used to describe the service entities/types names and display name.</p>
 */
public class DefaultMetaDataKey implements MetaDataKey, TypeMetaDataModel {

    private static final String DEFAULT_CATEGORY = "DEFAULT";

    private String id;
	private String displayName;
    private String category;
    private MetaDataPropertyManager<MetaDataKeyProperty> metaDataKeyPropertiesManager;

    /**
     * Simple {@link DefaultMetaDataKey} constructor. You should use this for most use cases.
     * @param id The XML name for the entity.
     * @param displayName The name that will be displayed on Studio UI.
     */
	public DefaultMetaDataKey(String id, String displayName) {
        this(id, displayName,  new ArrayList<MetaDataKeyProperty>());
        metaDataKeyPropertiesManager.addProperty(new DsqlFromMetaDataKeyProperty());
    }

    /**
     * <p>This is an advanced constructor. You should use the {@code DefaultMetaDataKey(String id, String displayName)} for most use cases.</p>
     * <p>This is intended for advanced scenarios like <strong>DSQL</strong> or <strong>Grouping types</strong>.</p>
     * @param id The XML name for the entity.
     * @param displayName The name that will be displayed on Studio UI.
     * @param keyProperties Properties used for advanced scenarios. Some of them are {@link DsqlFromMetaDataKeyProperty} or {@link org.mule.common.metadata.key.property.TypeDescribingProperty}.
     */
    public DefaultMetaDataKey(String id, String displayName, List<MetaDataKeyProperty> keyProperties) {
        this.id = id;
        this.displayName = displayName;
        this.category = DEFAULT_CATEGORY;
        metaDataKeyPropertiesManager = new MetaDataPropertyManager<MetaDataKeyProperty>(keyProperties);
    }

    @Deprecated
    public DefaultMetaDataKey(String id, String displayName, boolean isFromCapable) {
        this.id = id;
        this.displayName = displayName;
        this.category = DEFAULT_CATEGORY;
        metaDataKeyPropertiesManager = new MetaDataPropertyManager<MetaDataKeyProperty>();
        if (isFromCapable){
            metaDataKeyPropertiesManager.addProperty(new DsqlFromMetaDataKeyProperty());
        }
    }

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getDisplayName() {
		return displayName;
	}

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public List<MetaDataKeyProperty> getProperties() {
        return this.metaDataKeyPropertiesManager.getProperties();
    }

    @Override
    public boolean addProperty(MetaDataKeyProperty metaDataKeyProperty) {
        return this.metaDataKeyPropertiesManager.addProperty(metaDataKeyProperty);
    }

    @Override
    public boolean removeProperty(MetaDataKeyProperty metaDataKeyProperty) {
        return this.metaDataKeyPropertiesManager.removeProperty(metaDataKeyProperty);
    }

    @Override
    public boolean hasProperty(Class<? extends MetaDataKeyProperty> metaDataKeyProperty) {
        return this.metaDataKeyPropertiesManager.hasProperty(metaDataKeyProperty);
    }

    @Override
    public <T extends MetaDataKeyProperty> T getProperty(Class<T> metaDataKeyProperty) {
        return this.metaDataKeyPropertiesManager.getProperty(metaDataKeyProperty);
    }

    @Override
	public String toString() {
		return "DefaultMetaDataKey:{ displayName:" + displayName + " id:" + id + " category:" + category+ " }";
	}

	@Override
	public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof DefaultMetaDataKey)) return false;

        DefaultMetaDataKey that = (DefaultMetaDataKey) obj;

        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
	}

    /**
     * For keys comparison, the first criteria to match will be the {@link #category} of both keys, where, if it's not
     * possible to discriminate the order, the {@link #id} will take place.
     *
     * @param otherMetadataKey the key to be compared with
     * @return a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
     */
	@Override
	public int compareTo(MetaDataKey otherMetadataKey) {
        int res = category.compareTo(otherMetadataKey.getCategory());
        if (res != 0){
            return res;
        }
		return id.compareTo(otherMetadataKey.getId());
	}

    /**
     * @deprecated use {@link #hasProperty(Class)}  instead
     */
    @Deprecated
    @Override
    public boolean isFromCapable() {
        return metaDataKeyPropertiesManager.hasProperty(DsqlFromMetaDataKeyProperty.class);
    }
}
