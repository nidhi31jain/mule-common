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

import org.mule.common.metadata.key.property.CategoryKeyProperty;
import org.mule.common.metadata.key.property.MetaDataKeyProperty;
import org.mule.common.metadata.key.property.dsql.DsqlFromMetaDataKeyProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>{@link MetaDataKey} default implementation. This should be used to describe the service entities/types names and display name.</p>
 * @see {@link #equals(Object)}
 * @see {@link #hashCode()}
 * */
public class DefaultMetaDataKey implements MetaDataKey, TypeMetaDataModel {

	private String id;
	private String displayName;
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
        metaDataKeyPropertiesManager = new MetaDataPropertyManager<MetaDataKeyProperty>(keyProperties);
    }

    @Deprecated
    public DefaultMetaDataKey(String id, String displayName, boolean isFromCapable) {
        this.id = id;
        this.displayName = displayName;
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
		return "DefaultMetaDataKey:{ displayName:" + displayName + " id:" + id + " }";
	}

    /**
     * As this class is a bridge between DevKit and Studio for DataSense purposes, sometimes keys may have the same
     * {@link #id} but might represent different objects.
     * <p>Those cases arises when, for the same connector, the list of retrieved keys have grouping types, and therefore
     * the IDs can be repeated but for different categories (AKA {@link CategoryKeyProperty}).</p>
     * <p>Thus, a special type of {@link #equals(Object)} has to be implemented, where the category
     * {@link CategoryKeyProperty} plays a more important role than the others properties
     * within the {@link #metaDataKeyPropertiesManager}</p>
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the obj argument; false otherwise.
     * @see {@link #hashCode()}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultMetaDataKey)) return false;

        DefaultMetaDataKey that = (DefaultMetaDataKey) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        CategoryKeyProperty thisCategoryKeyProperty = this.getProperty(CategoryKeyProperty.class);
        CategoryKeyProperty thatCategoryKeyProperty = that.getProperty(CategoryKeyProperty.class);
        if (thisCategoryKeyProperty != null ? !thisCategoryKeyProperty.equals(thatCategoryKeyProperty) : thatCategoryKeyProperty != null){
            return false;
        }
        return true;
    }

    /**
     * As this class is a bridge between DevKit and Studio for DataSense purposes, sometimes keys may have the same
     * {@link #id} but might represent different objects.
     * <p>Those cases arises when, for the same connector, the list of retrieved keys have grouping types, and therefore
     * the IDs can be repeated but for different categories (AKA {@link CategoryKeyProperty}).</p>
     * <p>Thus, a special type of {@link #hashCode()} has to be implemented, where the category
     * {@link CategoryKeyProperty} plays a more important role than the others properties
     * within the {@link #metaDataKeyPropertiesManager}</p>
     *
     * @return a hash code value for this object.
     * @see {@link #equals(Object)}
     */
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (this.getProperty(CategoryKeyProperty.class) != null ? this.getProperty(CategoryKeyProperty.class).hashCode() : 0);
        return result;
    }

	@Override
	public int compareTo(MetaDataKey otherMetadataKey) {
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
