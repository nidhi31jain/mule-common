/**
 *
 */
package org.mule.common.metadata.builder;

import org.mule.common.metadata.DefaultMetaDataField;
import org.mule.common.metadata.MetaDataField;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.field.property.ImplementationClassMetaDataFieldProperty;
import org.mule.common.metadata.field.property.MetaDataFieldProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetaDataFieldBuilder
{

    private String name;
    private List<MetaDataFieldProperty> capabilities;
    private MetaDataField.FieldAccessType accessType;
    private MetaDataBuilder builder;
    private String className;

    MetaDataFieldBuilder(String name, MetaDataBuilder builder)
    {
        this.name = name;
        this.builder = builder;
        this.capabilities = new ArrayList<MetaDataFieldProperty>();
    }


    public void withProperty(MetaDataFieldProperty... properties)
    {
        capabilities.addAll(Arrays.asList(properties));
    }


    public void withAccessType(MetaDataField.FieldAccessType accessType)
    {
        this.accessType = accessType;
    }


    public void withImplClass(String className)
    {
        this.className = className;
    }


    public MetaDataField build()
    {

        MetaDataModel model = builder.build();
        if (className == null)
        {
            className = model.getDataType().toString();
        }

        capabilities.add(new ImplementationClassMetaDataFieldProperty(className));

        if (accessType == null)
        {
            accessType = MetaDataField.FieldAccessType.READ_WRITE;
        }
        return new DefaultMetaDataField(name, model, accessType, capabilities);
    }
}
