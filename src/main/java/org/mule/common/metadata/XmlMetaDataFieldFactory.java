package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;
import org.mule.common.metadata.property.QNameMetaDataProperty;
import org.mule.common.metadata.property.xml.AttributeMetaDataFieldProperty;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.xmlbeans.SchemaGlobalElement;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlException;

/**
 * Field Factory For XML Structured
 */
public class XmlMetaDataFieldFactory implements MetaDataFieldFactory
{

    private static final Map<QName, DataType> typeMapping = new HashMap<QName, DataType>();

    static
    {
        typeMapping.put(XmlConstants.XSD_BYTE, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_INT, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_INTEGER, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_SHORT, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_NEGATIVEINTEGER, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_NONNEGATIVEINTEGER, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_NONPOSITIVEINTEGER, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_POSITIVEINTEGER, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_UNSIGNEDBYTE, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_UNSIGNEDINT, DataType.INTEGER);
        typeMapping.put(XmlConstants.XSD_UNSIGNEDSHORT, DataType.INTEGER);

        typeMapping.put(XmlConstants.XSD_LONG, DataType.LONG);
        typeMapping.put(XmlConstants.XSD_UNSIGNEDLONG, DataType.LONG);

        typeMapping.put(XmlConstants.XSD_FLOAT, DataType.DOUBLE);
        typeMapping.put(XmlConstants.XSD_DOUBLE, DataType.DOUBLE);

        typeMapping.put(XmlConstants.XSD_BOOLEAN, DataType.BOOLEAN);

        typeMapping.put(XmlConstants.XSD_STRING, DataType.STRING);
        typeMapping.put(XmlConstants.XSD_ANY, DataType.STRING);

        typeMapping.put(XmlConstants.XSD_NORMALIZEDSTRING, DataType.STRING);

        typeMapping.put(XmlConstants.XSD_DECIMAL, DataType.DECIMAL);

        typeMapping.put(XmlConstants.XSD_DATE, DataType.DATE);
        typeMapping.put(XmlConstants.XSD_DATETIME, DataType.DATE_TIME);

        typeMapping.put(XmlConstants.XSD_HEXBIN, DataType.BYTE);
        typeMapping.put(XmlConstants.XSD_BASE64, DataType.BYTE);
    }

    public static final String PREFIX = "ns";

    private SchemaProvider schemas;
    private QName rootElementName;
    //private Charset encoding;
    private Map<String, String> namespacePrefix = new HashMap<String, String>();

    public XmlMetaDataFieldFactory(SchemaProvider schemas, QName rootElementName)
    {
        this.schemas = schemas;
        this.rootElementName = rootElementName;
    }

    @Override
    public List<MetaDataField> createFields()
    {

        List<MetaDataField> metaDataFields = new ArrayList<MetaDataField>();
        final Map<SchemaType, XmlMetaDataModel> visitedTypes = new HashMap<SchemaType, XmlMetaDataModel>();
        try
        {
            SchemaGlobalElement rootElement = schemas.findRootElement(rootElementName);
            if (rootElement != null)
            {
                SchemaType type = rootElement.getType();
                loadFields(type, metaDataFields, visitedTypes);
            }
        }
        catch (XmlException e)
        {
            throw new MetaDataGenerationException(e);
        }
        return metaDataFields;
    }

    private void loadFields(SchemaType type, List<MetaDataField> metaDataFields, Map<SchemaType, XmlMetaDataModel> visitedTypes)
    {

        if (isSimpleType(type))
        {
            DataType dataType = getDataType(type, DataType.STRING);
            metaDataFields.add(new DefaultMetaDataField("text()", new DefaultSimpleMetaDataModel(dataType), new QNameMetaDataProperty(new QName("text()"))));
        }
        else
        {
            SchemaProperty[] properties = type.getProperties();
            for (SchemaProperty property : properties)
            {
                QName name = setPrefixIfNotPresent(property.getName());
                //
                SchemaType propertyType = property.getType();
                if (property.isAttribute())
                {
                    DataType dataType = getDataType(propertyType, DataType.STRING);
                    metaDataFields.add(new DefaultMetaDataField(toLabel(name), new DefaultSimpleMetaDataModel(dataType), new QNameMetaDataProperty(name), new AttributeMetaDataFieldProperty(true)));
                }
                else if (isList(property))
                {
                    if (isSimpleType(propertyType))
                    {
                        DataType dataType = getDataType(propertyType, DataType.STRING);
                        metaDataFields.add(new DefaultMetaDataField(toLabel(name), new DefaultListMetaDataModel(new DefaultSimpleMetaDataModel(dataType)), new QNameMetaDataProperty(name)));
                    }
                    else
                    {
                        XmlMetaDataModel model = buildXMLMetaDataModel(visitedTypes, propertyType);
                        metaDataFields.add(new DefaultMetaDataField(toLabel(name), new DefaultListMetaDataModel(model), new QNameMetaDataProperty(name)));
                    }
                }
                else
                {
                    if (isSimpleType(propertyType))
                    {
                        DataType dataType = getDataType(propertyType, DataType.STRING);
                        metaDataFields.add(new DefaultMetaDataField(toLabel(name), new DefaultSimpleMetaDataModel(dataType), new QNameMetaDataProperty(name)));
                    }
                    else
                    {
                        XmlMetaDataModel model = buildXMLMetaDataModel(visitedTypes, propertyType);
                        metaDataFields.add(new DefaultMetaDataField(toLabel(name), model, new QNameMetaDataProperty(name)));
                    }
                }
            }

        }

    }


    private QName setPrefixIfNotPresent(QName name)
    {
        if (name.getNamespaceURI().isEmpty())
        {
            return name;
        }
        else if (!prefixIsDeclared(name))
        {
            if (namespacePrefix.containsKey(name.getNamespaceURI()))
            {
                return new QName(name.getNamespaceURI(), name.getLocalPart(), namespacePrefix.get(name.getNamespaceURI()));
            }
            else
            {
                int size = namespacePrefix.size();
                String prefix;
                do
                {
                    prefix = PREFIX + size;
                    size++;
                }
                while (namespacePrefix.containsKey(prefix));
                namespacePrefix.put(name.getNamespaceURI(), prefix);
                return new QName(name.getNamespaceURI(), name.getLocalPart(), prefix);
            }
        }
        return name;

    }

    private XmlMetaDataModel buildXMLMetaDataModel(Map<SchemaType, XmlMetaDataModel> visitedTypes, SchemaType propertyType)
    {
        XmlMetaDataModel model;
        if (visitedTypes.containsKey(propertyType))
        {
            model = visitedTypes.get(propertyType);
        }
        else
        {
            ArrayList<MetaDataField> fields = new ArrayList<MetaDataField>();
            model = new DefaultXmlMetaDataModel(schemas, rootElementName, fields);
            visitedTypes.put(propertyType, model);
            loadFields(propertyType, fields, visitedTypes);
        }
        return model;
    }

    private String toLabel(QName name)
    {
        if (prefixIsDeclared(name))
        {
            return name.getPrefix() + ":" + name.getLocalPart();
        }
        else
        {
            return name.getLocalPart();
        }
    }

    private boolean prefixIsDeclared(QName name)
    {
        return name.getPrefix() != null && !name.getPrefix().isEmpty();
    }


    private boolean hasSimpleContentOnly(SchemaType type)
    {

        return hasSimpleContent(type) && isPlainElement(type);
    }

    private boolean hasSimpleContent(SchemaType type)
    {

        return type.isSimpleType() || type.getContentType() == SchemaType.SIMPLE_CONTENT || type.getContentType() == SchemaType.MIXED_CONTENT
               || type.getComponentType() == SchemaType.EMPTY_CONTENT;
    }

    private boolean isList(SchemaProperty property)
    {
        BigInteger maxOccurs = property.getMaxOccurs();
        return maxOccurs == null || property.getMaxOccurs().intValue() > 1;
    }

    private DataType getDataType(SchemaType type, DataType defaultDataType)
    {
        SchemaType simpleType = getSimpleBaseType(type);

        do
        {
            DataType fieldType = typeMapping.get(simpleType.getName());
            if (fieldType != null)
            {
                return fieldType;
            }

            simpleType = simpleType.getBaseType();
        }
        while (simpleType != null);

        return defaultDataType;
    }

    private boolean isSimpleType(SchemaType type)
    {
        return getDataType(type, null) != null && hasSimpleContentOnly(type);
    }

    /**
     * Element is plain if it has not attribute or properties
     *
     * @param type
     * @return
     */
    private boolean isPlainElement(SchemaType type)
    {
        return (type.getAttributeProperties() == null || type.getAttributeProperties().length == 0)
               && (type.getElementProperties() == null || type.getElementProperties().length == 0);
    }

    private static SchemaType getSimpleBaseType(SchemaType type)
    {
        SchemaType basic = type;
        while (basic.getBaseType() != null && !basic.isSimpleType())
        {
            basic = basic.getBaseType();
        }

        return basic;
    }
}
