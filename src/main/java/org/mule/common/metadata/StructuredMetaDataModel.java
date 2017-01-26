package org.mule.common.metadata;

import java.util.List;

/**
 * Represents meta data model that its structured can be described with a list of fields.
 */
public interface StructuredMetaDataModel extends MetaDataModel
{

    List<MetaDataField> getFields();

    MetaDataField getFieldByName(String name);

    /**
     * @return true if the type definition allows for any element type, such as in:
     * 
     *         <pre>
     * {@code
     *    <xs:complexType name="anyParams">
     *        <xs:sequence>
     *            <xs:any minOccurs="0" maxOccurs="1"/>
     *        </xs:sequence>
     *    </xs:complexType>
     * }
     *         </pre>
     * 
     * @since 3.9.0
     */
    boolean isAnyFieldAllowed();
}
