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
import static org.hamcrest.CoreMatchers.notNullValue;
import org.mule.common.metadata.XmlMetaDataModel;
import org.mule.common.metadata.builder.DefaultXmlMetaDataBuilder;
import org.mule.common.metadata.builder.MetaDataBuilder;
import org.mule.common.metadata.property.DescriptionMetaDataProperty;
import org.mule.common.metadata.property.LabelMetaDataProperty;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.apache.xmlbeans.XmlBeans;
import org.junit.Assert;
import org.junit.Test;

public class XmlMetaDataBuilderTestCase
{

   final String schema = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
                    + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">"
                    + "<xs:element name=\"shipto\">\n"
                    + "  <xs:complexType>\n"
                    + "    <xs:sequence>\n"
                    + "      <xs:element name=\"name\" type=\"xs:string\"/>\n"
                    + "      <xs:element name=\"address\" type=\"xs:string\"/>\n"
                    + "      <xs:element name=\"city\" type=\"xs:int\"/>\n"
                    + "      <xs:element name=\"country\" type=\"xs:boolean\"/>\n"
                    + "    </xs:sequence>\n"
                    + "  </xs:complexType>\n"
                    + "</xs:element>"
                    + "</xs:schema>";

    @Test()
    public void testDefaultXmlMetaDataBuilderBuildMethod() throws IOException
    {
        final String rootName = "$root-name$";
        final Charset encoding = Charset.forName("UTF-8");
        final String[] schemas = {schema};
        final String example = "$example$";

        DefaultXmlMetaDataBuilder<MetaDataBuilder<?>> metaDataBuilder = new DefaultXmlMetaDataBuilder<MetaDataBuilder<?>>(new QName(rootName));
        metaDataBuilder.setEncoding(encoding);
        metaDataBuilder.addSchemaStringList(schemas).setExample(example);
        XmlMetaDataModel model = metaDataBuilder.build();
        Assert.assertThat(XmlBeans.NO_TYPE, notNullValue());
        Assert.assertThat(model.getRootElement(), is(new QName(rootName)));
        Assert.assertThat(model.getExample(), is(example));
        Assert.assertThat(model.getSchemas().size(), is(schemas.length));
        for (int i = 0; i < schemas.length; i++)
        {
            Assert.assertThat(IOUtils.toString(model.getSchemas().get(i), encoding.toString()), is(schemas[i]));
        }
    }


    @Test()
    public void whenLabelAndDescriptionAreSetInTheBuilderTheyShouldBePresentInTheResult() throws IOException
    {
        final String rootName = "$root-name$";
        final Charset encoding = Charset.forName("UTF-8");
        final String[] schemas = {schema};
        final String example = "$example$";
        final String label = "Label";
        final String description = "Description";

        DefaultXmlMetaDataBuilder<MetaDataBuilder<?>> metaDataBuilder = new DefaultXmlMetaDataBuilder<MetaDataBuilder<?>>(new QName(rootName));
        metaDataBuilder.setEncoding(encoding);
        metaDataBuilder.setLabel(label);
        metaDataBuilder.setDescription(description);
        metaDataBuilder.addSchemaStringList(schemas).setExample(example);
        XmlMetaDataModel model = metaDataBuilder.build();
        Assert.assertThat(model.getRootElement(), is(new QName(rootName)));
        Assert.assertThat(model.getExample(), is(example));
        Assert.assertThat(model.getSchemas().size(), is(schemas.length));
        Assert.assertThat(model.getProperty(LabelMetaDataProperty.class).getLabel(), is(label));
        Assert.assertThat(model.getProperty(DescriptionMetaDataProperty.class).getDescription(), is(description));
        for (int i = 0; i < schemas.length; i++)
        {
            Assert.assertThat(IOUtils.toString(model.getSchemas().get(i), encoding.toString()), is(schemas[i]));
        }
    }


}



