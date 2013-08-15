package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

import java.util.List;

/**
 */
public interface FieldFeatureFactory {
    public List<Capability> getCapabilities(String name, DataType dataType);
}
