package org.mule.common.metadata.property;

import org.mule.common.metadata.MetaDataModelProperty;

/**
 * String based example property. Is used to set an example of the metadata. I.E A XML example of the metadata
 */
public class TextBasedExampleMetaDataModelProperty implements MetaDataModelProperty
{

    private String exampleContent;

    public TextBasedExampleMetaDataModelProperty(String exampleContent)
    {
        this.exampleContent = exampleContent;
    }

    public String getExampleContent()
    {
        return exampleContent;
    }

    public void setExampleContent(String exampleContent)
    {
        this.exampleContent = exampleContent;
    }
}
