/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.common.metadata.test.builder;

import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.common.metadata.XmlMetaDataModel;
import org.mule.common.metadata.builder.DefaultXmlMetaDataBuilder;
import org.mule.common.metadata.builder.MetaDataBuilder;

public class XmlMetaDataBuilderTestCase
{

    @Test()
    public void testDefaultXmlMetaDataBuilderBuildMethod() throws IOException
    {
        final String rootName = "$root-name$";
        final Charset encoding = Charset.forName("UTF-8");
        final String[] schemas = {"$schema(0)$"};
        final String example = "$example$";
        
        DefaultXmlMetaDataBuilder<MetaDataBuilder<?>> metaDataBuilder = new DefaultXmlMetaDataBuilder<MetaDataBuilder<?>>(rootName);
        metaDataBuilder.setEncoding(encoding);
        metaDataBuilder.addSchemaStringList(schemas).setExample(example);
        XmlMetaDataModel model = metaDataBuilder.build();
        Assert.assertThat(model.getRootElement(), is(rootName));
        Assert.assertThat(model.getExample(), is(example));
        Assert.assertThat(model.getSchemas().size(), is(schemas.length));
        for(int i=0; i < schemas.length; i++)
        {
            Assert.assertThat(IOUtils.toString(model.getSchemas().get(i), encoding.toString()), is(schemas[i]));
        }
    }

    
}



