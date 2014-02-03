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

public class DefaultMetaDataKey implements MetaDataKey, TypeMetaDataModel {

	private String id;
	private String displayName;
    private MetaDataPropertyManager<MetaDataKeyProperty> metaDataKeyPropertiesManager;

	public DefaultMetaDataKey(String id, String displayName) {
        this(id, displayName,  new ArrayList<MetaDataKeyProperty>());
        metaDataKeyPropertiesManager.addProperty(new DsqlFromMetaDataKeyProperty());
    }

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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultMetaDataKey other = (DefaultMetaDataKey) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
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
