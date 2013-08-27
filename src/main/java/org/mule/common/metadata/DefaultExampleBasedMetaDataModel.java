/**
 *
 */
package org.mule.common.metadata;

import org.mule.common.metadata.datatype.DataType;

public class DefaultExampleBasedMetaDataModel extends AbstractMetaDataModel implements ExampleBasedMetaDataModel
{

    private String example;

    protected DefaultExampleBasedMetaDataModel(DataType dataType, String example)
    {
        super(dataType);
        this.example = example;
    }

    @Override
    public String getExampleContent()
    {
        return example;
    }

    @Override
    public void accept(MetaDataModelVisitor modelVisitor)
    {
    }

}
