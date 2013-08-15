package org.mule.common.metadata;

import org.mule.common.metadata.Capability;
import org.mule.common.metadata.FieldFeatureFactory;
import org.mule.common.metadata.DefaultQueryCapability;
import org.mule.common.metadata.datatype.DataType;

import java.util.Arrays;
import java.util.List;

/**
 */
public class DefaultFieldFeatureFactory implements FieldFeatureFactory {
    @Override
    public List<Capability> getCapabilities(String name, DataType dataType) {
        return Arrays.<Capability>asList(new DefaultQueryCapability());
    }
}
