package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataKey;
import org.mule.common.metadata.MetaDataModel;
import org.mule.common.metadata.MetaDataParameterMode;
import org.mule.common.metadata.MetaDataModelProperty;
import org.mule.common.metadata.MetaDataBehaviourMode;

/**
 * <p>Property that will be shipped with each described {@link MetaDataModel} from within a
 * connector when an {@link MetaDataKey} is provided.</p>
 * <p>It's used for two main reasons:
 * <ul>
 *     <li>DevKit side: This property will help the connector's developer to discriminate between input or output
 *     metadata.</li>
 *     <li>Studio side: useful to properly store the provided meta data from the connector within the Studio's
 *      Catalog, and then render it for future uses in the MetaData Tree Explorer</li>
 * </ul>
 * </p>
 */
public class CatalogTypeProperty implements MetaDataModelProperty
{

    /**
     * <p>If the described entity is {@link MetaDataBehaviourMode#STATIC} the value must be the fully qualified name of
     * the POJO.</p>
     * <p>When the type maps to a {@link MetaDataBehaviourMode#DYNAMIC}, the value can be either the same as the
     * described key (the one provided by {@link MetaDataKey#getId()}), or it might have a suffix discriminating it as
     * " Result".
     * <br/>
     * E.g.: for the "Account" type (the value obtained from {@link MetaDataKey#getId()}), when describing
     * {@link MetaDataParameterMode#INPUT} should be "Account", while when describing {@link MetaDataParameterMode#OUTPUT}
     * should be "Account Result"
     * </p>
     */
    private String name;

    private MetaDataParameterMode parameterMode;
    private MetaDataBehaviourMode behaviourMode;

    public CatalogTypeProperty(String name, MetaDataParameterMode parameterMode, MetaDataBehaviourMode behaviourMode)
    {
        this.name = name;
        this.parameterMode = parameterMode;
        this.behaviourMode = behaviourMode;
    }

    public String getName()
    {
        return name;
    }

    public MetaDataParameterMode getParameterMode()
    {
        return parameterMode;
    }

    public MetaDataBehaviourMode getBehaviourMode()
    {
        return behaviourMode;
    }
}
