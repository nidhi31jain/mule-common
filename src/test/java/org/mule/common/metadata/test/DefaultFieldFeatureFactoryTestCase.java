package org.mule.common.metadata.test;

import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.Capability;
import org.mule.common.metadata.DefaultFieldFeatureFactory;
import org.mule.common.metadata.QueryCapability;
import org.mule.common.metadata.datatype.DataType;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 */
public class DefaultFieldFeatureFactoryTestCase {

    private DefaultFieldFeatureFactory defaultFieldFeatureFactory;

    @Before
    public void setUp(){
        this.defaultFieldFeatureFactory = new DefaultFieldFeatureFactory();
    }

    @Test
    public void testGetCapabilities(){
        List<Capability> capabilityList = defaultFieldFeatureFactory.getCapabilities("SomeFieldName", DataType.STRING);
        assertNotNull(capabilityList);
        assertTrue(capabilityList.size() == 1);
        assertTrue(capabilityList.get(0) instanceof QueryCapability);

        capabilityList = defaultFieldFeatureFactory.getCapabilities("SomeFieldName", DataType.NUMBER);
        assertNotNull(capabilityList);
        assertTrue(capabilityList.size() == 1);
        assertTrue(capabilityList.get(0) instanceof QueryCapability);
    }

}
