package org.mule.common.metadata.test;

import org.mule.common.metadata.ListMetaDataModel;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataGenerationException;
import org.mule.common.metadata.SimpleMetaDataModel;
import org.mule.common.metadata.XmlMetaDataModel;
import org.mule.common.metadata.builder.DefaultMetaDataBuilder;
import org.mule.common.metadata.builder.XmlMetaDataBuilder;
import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.QNameMetaDataProperty;
import org.mule.common.metadata.property.xml.AttributeMetaDataFieldProperty;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

/**
 * Test XmlMetaDataModelIntrospection
 */
public class XmlMetaDataModelTest
{


    @Test
    public void whenSimpleTypesAreDeclaredMetadataShouldHaveSimpleMetadataModel()
    {


        String schema = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
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

        QName rootElementName = new QName("shipto");
        XmlMetaDataModel shipto = new DefaultMetaDataBuilder().createXmlObject(rootElementName).addSchemaStringList(schema).build();
        Assert.assertThat(shipto.getRootElement(), CoreMatchers.is(rootElementName));
        Assert.assertThat(shipto.getFields().size(), CoreMatchers.is(4));

        Assert.assertThat(shipto.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(shipto.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(shipto.getFields().get(0).getName(), CoreMatchers.is("name"));

        Assert.assertThat(shipto.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(shipto.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(shipto.getFields().get(1).getName(), CoreMatchers.is("address"));

        Assert.assertThat(shipto.getFields().get(2).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(shipto.getFields().get(2).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.INTEGER));
        Assert.assertThat(shipto.getFields().get(2).getName(), CoreMatchers.is("city"));

        Assert.assertThat(shipto.getFields().get(3).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(shipto.getFields().get(3).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.BOOLEAN));
        Assert.assertThat(shipto.getFields().get(3).getName(), CoreMatchers.is("country"));
    }


    @Test
    public void whenAttributeIsDeclaredAttributePropertyShouldBeSet() throws Exception
    {

        String schema = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
                        + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n" + "\n"
                        + "<xs:element name=\"person\">\n"
                        + "<xs:complexType>\n"
                        + "<xs:attribute name=\"name\" type=\"xs:string\" use=\"required\"/>\n"
                        + "<xs:attribute name=\"surname\" type=\"xs:int\" use=\"required\"/>\n"
                        + "</xs:complexType>\n"
                        + "</xs:element>\n"
                        + "\n"
                        + "</xs:schema>";


        QName rootElementName = new QName("person");
        XmlMetaDataModel person = new DefaultMetaDataBuilder().createXmlObject(rootElementName).addSchemaStringList(schema).build();
        Assert.assertThat(person.getRootElement(), CoreMatchers.is(rootElementName));
        Assert.assertThat(person.getFields().size(), CoreMatchers.is(2));

        Assert.assertThat(person.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(person.getFields().get(0).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.STRING));
        Assert.assertThat(person.getFields().get(0).getName(), CoreMatchers.is("name"));
        Assert.assertThat(person.getFields().get(0).getProperty(AttributeMetaDataFieldProperty.class).isAttribute(), CoreMatchers.is(true));


        Assert.assertThat(person.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(person.getFields().get(1).getMetaDataModel().getDataType(), CoreMatchers.is(DataType.INTEGER));
        Assert.assertThat(person.getFields().get(1).getName(), CoreMatchers.is("surname"));
        Assert.assertThat(person.getFields().get(1).getProperty(AttributeMetaDataFieldProperty.class).isAttribute(), CoreMatchers.is(true));

    }


    @Test
    public void whenMaxOccursIsSetToUnboundedAListShouldBeSet() throws Exception
    {

        String schema = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n"
                        + "<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"
                        + "\n"
                        + "<xs:element name=\"shiporder\">\n"
                        + "  <xs:complexType>\n"
                        + "    <xs:sequence>\n"
                        + "      <xs:element name=\"orderperson\" type=\"xs:string\"/>\n"
                        + "      <xs:element name=\"shipto\">\n"
                        + "        <xs:complexType>\n"
                        + "          <xs:sequence>\n"
                        + "            <xs:element name=\"name\" type=\"xs:string\"/>\n"
                        + "            <xs:element name=\"address\" type=\"xs:string\"/>\n"
                        + "            <xs:element name=\"city\" type=\"xs:string\"/>\n"
                        + "            <xs:element name=\"country\" type=\"xs:string\"/>\n"
                        + "          </xs:sequence>\n"
                        + "        </xs:complexType>\n"
                        + "      </xs:element>\n"
                        + "      <xs:element name=\"item\" maxOccurs=\"unbounded\">\n"
                        + "        <xs:complexType>\n"
                        + "          <xs:sequence>\n"
                        + "            <xs:element name=\"title\" type=\"xs:string\"/>\n"
                        + "            <xs:element name=\"note\" type=\"xs:string\" minOccurs=\"0\"/>\n"
                        + "            <xs:element name=\"quantity\" type=\"xs:positiveInteger\"/>\n"
                        + "            <xs:element name=\"price\" type=\"xs:decimal\"/>\n"
                        + "          </xs:sequence>\n"
                        + "        </xs:complexType>\n"
                        + "      </xs:element>\n"
                        + "    </xs:sequence>\n"
                        + "    <xs:attribute name=\"orderid\" type=\"xs:string\" use=\"required\"/>\n"
                        + "  </xs:complexType>\n"
                        + "</xs:element>\n" + "\n"
                        + "</xs:schema>";


        QName rootElementName = new QName("shiporder");
        XmlMetaDataModel person = new DefaultMetaDataBuilder().createXmlObject(rootElementName).addSchemaStringList(schema).build();
        List<MetaDataField> fields = person.getFields();
        Assert.assertThat(fields.size(), CoreMatchers.is(4));
        Assert.assertThat(fields.get(0).getName(), CoreMatchers.is("orderperson"));
        Assert.assertThat(fields.get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));


        Assert.assertThat(fields.get(1).getName(), CoreMatchers.is("shipto"));
        Assert.assertThat(fields.get(1).getMetaDataModel(), CoreMatchers.instanceOf(XmlMetaDataModel.class));
        Assert.assertThat(((XmlMetaDataModel) fields.get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(4));


        Assert.assertThat(fields.get(2).getName(), CoreMatchers.is("item"));
        Assert.assertThat(fields.get(2).getMetaDataModel(), CoreMatchers.instanceOf(ListMetaDataModel.class));
        Assert.assertThat(((ListMetaDataModel) fields.get(2).getMetaDataModel()).getElementModel(), CoreMatchers.instanceOf(XmlMetaDataModel.class));
        Assert.assertThat(((XmlMetaDataModel) ((ListMetaDataModel) fields.get(2).getMetaDataModel()).getElementModel()).getFields().size(), CoreMatchers.is(4));


        Assert.assertThat(fields.get(3).getName(), CoreMatchers.is("orderid"));
        Assert.assertThat(fields.get(3).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(fields.get(3).getProperty(AttributeMetaDataFieldProperty.class).isAttribute(), CoreMatchers.is(true));

    }


    @Test
    public void whenNamespaceIsDeclaredTheyShouldBePresentOnTheGeneratedMetaData()
    {
        String schema = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" targetNamespace=\"http://datypic.com/ord\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"
                        + "  <xs:element name=\"order\" type=\"ord:orderType\" xmlns:ord=\"http://datypic.com/ord\"/>\n"
                        + "  <xs:complexType name=\"itemsType\">\n"
                        + "    <xs:sequence>\n"
                        + "      <xs:element ref=\"prod:product\" xmlns:prod=\"http://datypic.com/prod\"/>\n"
                        + "    </xs:sequence>\n"
                        + "  </xs:complexType>\n"
                        + "  <xs:complexType name=\"orderType\">\n"
                        + "    <xs:sequence>\n"
                        + "      <xs:element type=\"xs:string\" name=\"number\"/>\n"
                        + "      <xs:element type=\"ord:itemsType\" name=\"items\" xmlns:ord=\"http://datypic.com/ord\"/>\n"
                        + "    </xs:sequence>\n"
                        + "  </xs:complexType>\n"
                        + "</xs:schema>";

        String schema1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                         + "<xs:schema attributeFormDefault=\"unqualified\" elementFormDefault=\"qualified\" targetNamespace=\"http://datypic.com/prod\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n"
                         + "  <xs:element name=\"product\" type=\"prod:productType\" xmlns:prod=\"http://datypic.com/prod\"/>\n"
                         + "  <xs:complexType name=\"sizeType\">\n"
                         + "    <xs:simpleContent>\n"
                         + "      <xs:extension base=\"xs:byte\">\n"
                         + "        <xs:attribute type=\"xs:string\" name=\"system\"/>\n"
                         + "      </xs:extension>\n"
                         + "    </xs:simpleContent>\n"
                         + "  </xs:complexType>\n"
                         + "  <xs:complexType name=\"productType\">\n"
                         + "    <xs:sequence>\n"
                         + "      <xs:element type=\"xs:short\" name=\"number\"/>\n"
                         + "      <xs:element type=\"prod:sizeType\" name=\"size\" xmlns:prod=\"http://datypic.com/prod\"/>\n"
                         + "    </xs:sequence>\n"
                         + "  </xs:complexType>\n"
                         + "</xs:schema>";


        QName rootElementName = new QName("http://datypic.com/ord", "order");
        XmlMetaDataModel person = new DefaultMetaDataBuilder().createXmlObject(rootElementName).addSchemaStringList(schema, schema1).build();
        Assert.assertThat(person.getFields().size(), CoreMatchers.is(2));
        Assert.assertThat(person.getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(SimpleMetaDataModel.class));
        Assert.assertThat(person.getFields().get(1).getMetaDataModel(), CoreMatchers.instanceOf(XmlMetaDataModel.class));
        Assert.assertThat(person.getFields().get(1).getProperty(QNameMetaDataProperty.class).getName(), CoreMatchers.is(new QName("http://datypic.com/ord", "items", "ns0")));
        Assert.assertThat(person.getFields().get(1).getName(), CoreMatchers.is("ns0:items"));
        Assert.assertThat(((XmlMetaDataModel) person.getFields().get(1).getMetaDataModel()).getFields().size(), CoreMatchers.is(1));
        Assert.assertThat(((XmlMetaDataModel) person.getFields().get(1).getMetaDataModel()).getFields().get(0).getMetaDataModel(), CoreMatchers.instanceOf(XmlMetaDataModel.class));
        Assert.assertThat(((XmlMetaDataModel) person.getFields().get(1).getMetaDataModel()).getFields().get(0).getName(), CoreMatchers.is("ns1:product"));
        Assert.assertThat(((XmlMetaDataModel) person.getFields().get(1).getMetaDataModel()).getFields().get(0).getProperty(QNameMetaDataProperty.class).getName(), CoreMatchers.is(new QName("http://datypic.com/prod", "product", "ns1")));

    }

    @Test(expected = MetaDataGenerationException.class)
    public void whenSchemaIsCorruptedExceptionShouldBeThrown()
    {
        QName rootElementName = new QName("http://datypic.com/ord", "order");
        XmlMetaDataModel person = new DefaultMetaDataBuilder().createXmlObject(rootElementName).addSchemaStringList("bla....").build();
    }

    @Test
    public void whenSchemaHasSchemaTypeDeclarationItShouldNotFail()
    {

        QName rootElementName = new QName("http://paladyne.com/securitymaster/global/8.0/", "ExecuteAdvancedSearchTemplateResponse");

        XmlMetaDataBuilder xmlMetaDataBuilder = new DefaultMetaDataBuilder().createXmlObject(rootElementName);

        List<String> schemas = Arrays.asList("xsd/dotNet1.xsd", "xsd/dotNet2.xsd", "xsd/dotNet3.xsd", "xsd/dotNet4.xsd", "xsd/dotNet5.xsd", "xsd/dotNet6.xsd");
        for (String schema : schemas)
        {
            xmlMetaDataBuilder.addSchemaStreamList(getClass().getClassLoader().getResourceAsStream(schema));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) xmlMetaDataBuilder.build();
        Assert.assertThat(model, CoreMatchers.notNullValue());
    }

    @Test
    public void whenSchemaHeadersDeclarationItShouldNotFail()
    {
        QName rootElementName = new QName("urn:enterprise.soap.sforce.com", "describeGlobalResponse");

        XmlMetaDataBuilder xmlMetaDataBuilder = new DefaultMetaDataBuilder().createXmlObject(rootElementName);

        List<String> schemas = Arrays.asList("xsd/salesforce1.xsd", "xsd/salesforce2.xsd", "xsd/salesforce3.xsd");
        for (String schema : schemas)
        {
            xmlMetaDataBuilder.addSchemaStreamList(getClass().getClassLoader().getResourceAsStream(schema));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) xmlMetaDataBuilder.build();
        Assert.assertThat(model, CoreMatchers.notNullValue());
    }

    @Test
    public void whenSchemaUrlIncludeShouldBeSupported()
    {
        QName rootElementName = new QName("Envelope");

        XmlMetaDataBuilder xmlMetaDataBuilder = new DefaultMetaDataBuilder().createXmlObject(rootElementName);

        List<String> schemas = Arrays.asList("xsd/includes/eGalaxyMessages-OtherMessages.xsd");
        for (String schema : schemas)
        {
            xmlMetaDataBuilder.addSchemaUrlList(getClass().getClassLoader().getResource(schema));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) xmlMetaDataBuilder.build();
        Assert.assertThat(model, CoreMatchers.notNullValue());
    }

    @Test
    public void whenSchemaStringAndBaseUrlIncludeShouldBeSupported()
    {
        QName rootElementName = new QName("Envelope");

        XmlMetaDataBuilder xmlMetaDataBuilder = new DefaultMetaDataBuilder().createXmlObject(rootElementName);

        List<String> schemas = Arrays.asList("xsd/includes/eGalaxyMessages-OtherMessages.xsd");
        for (String schema : schemas)
        {
            xmlMetaDataBuilder.addSchemaStreamList(getClass().getClassLoader().getResourceAsStream(schema));
        }
        xmlMetaDataBuilder.setSourceUri(getClass().getClassLoader().getResource(schemas.get(0)));
        XmlMetaDataModel model = (XmlMetaDataModel) xmlMetaDataBuilder.build();
        Assert.assertThat(model, CoreMatchers.notNullValue());
    }

    @Test
    public void simpleValueOnly()
    {
        final XmlMetaDataBuilder builder = new DefaultMetaDataBuilder().createXmlObject(new QName("http://www.stichting-vera.nl/StUF/sector/vera/0310", "identifierValue"));
        List<String> schemas = Arrays.asList("xsd/simple.xsd");
        for (String schema : schemas)
        {
            builder.addSchemaStreamList(getClass().getClassLoader().getResourceAsStream(schema));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) builder.build();
        Assert.assertThat(model.getFields().size(), CoreMatchers.is(1));

    }

    @Test
    public void xsdWithTwoFields() throws IOException
    {
        final XmlMetaDataBuilder builder = new DefaultMetaDataBuilder().createXmlObject(new QName("http://www.loyaltylab.com/loyaltyapi/", "RedeemReward"));
        List<String> schemas = Arrays.asList("xsd/se-1664/in0.xsd","xsd/se-1664/in1.xsd","xsd/se-1664/in2.xsd");
        for (String schema : schemas)
        {
            builder.addSchemaStringList(IOUtils.toString(getClass().getClassLoader().getResourceAsStream(schema)));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) builder.build();
        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));

    }


    @Test
    public void testSE1577() throws IOException {
        final XmlMetaDataBuilder builder = new DefaultMetaDataBuilder().createXmlObject(new QName("http://epicor.com/webservices/", "AllowUndoReadyToQuote"));
        List<String> schemas = Arrays.asList("xsd/se_1577/schema-1.xsd","xsd/se_1577/schema-2.xsd");
        for (String schema : schemas)
        {
            builder.addSchemaStringList(IOUtils.toString(getClass().getClassLoader().getResourceAsStream(schema)));
        }
        XmlMetaDataModel model = (XmlMetaDataModel) builder.build();
        Assert.assertThat(model.getFields().size(), CoreMatchers.is(2));
    }
}

