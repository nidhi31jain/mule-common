package org.mule.common.metadata.util;

import junit.framework.Assert;
import org.junit.Test;
import org.mule.common.metadata.*;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.query.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MetaDataFilterTest {
    @Test
    public void testFilter() throws Exception {

        Map<String,MetaDataModel> map = new HashMap<String, MetaDataModel>();

        map.put("filterThis", new DefaultSimpleMetaDataModel(DataType.STRING));
        map.put("noFilterThis", new DefaultPojoMetaDataModel(DataType.class));
        map.put("nietherThis", new DefaultSimpleMetaDataModel(DataType.NUMBER));
        map.put("thisIsOk", new DefaultPojoMetaDataModel(MetaDataFilterTest.class));
        DefaultDefinedMapMetaDataModel model = new DefaultDefinedMapMetaDataModel(map, "Account");

        List<Field> filters = new ArrayList<Field>();
        filters.add(new Field("filterThis","type"));
        filters.add(new Field("thisIsOk","otherType"));

        FieldFilterVisitor visitor = new FieldFilterVisitor(filters);

        model.accept(visitor);

        Assert.assertEquals(2,((DefinedMapMetaDataModel)visitor.filteringResult().getPayload()).getKeys().size());

    }
}
