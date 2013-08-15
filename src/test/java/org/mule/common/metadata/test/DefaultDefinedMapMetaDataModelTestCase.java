package org.mule.common.metadata.test;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 */
public class DefaultDefinedMapMetaDataModelTestCase {

    private DefinedMapMetaDataModel mapMetaDataModel;

    @Before
    public void setUp(){
        Map<String, MetaDataModel> map = new HashMap<String, MetaDataModel>();
        map.put("field1", new DefaultSimpleMetaDataModel(DataType.STRING));
        map.put("field2", new DefaultSimpleMetaDataModel(DataType.NUMBER));

        mapMetaDataModel = new DefaultDefinedMapMetaDataModel(map, "mapName");
    }

    @Test
    public void testKeys(){
        assertTrue(mapMetaDataModel.getKeys().contains("field1"));
        assertTrue(mapMetaDataModel.getKeys().contains("field2"));
    }

    @Test
    public void testValues(){
        assertTrue(mapMetaDataModel.getValueMetaDataModel("field1").getDataType().equals(DataType.STRING));
        assertTrue(mapMetaDataModel.getValueMetaDataModel("field2").getDataType().equals(DataType.NUMBER));
    }

    @Test
    public void testFields(){
        assertNotNull(mapMetaDataModel.getFields());
        assertEquals(2, mapMetaDataModel.getFields().size());
        assertTrue(mapMetaDataModel.getFields().get(1).getMetaDataModel().getDataType().equals(DataType.STRING));
        assertTrue(mapMetaDataModel.getFields().get(0).getMetaDataModel().getDataType().equals(DataType.NUMBER));
    }
}
