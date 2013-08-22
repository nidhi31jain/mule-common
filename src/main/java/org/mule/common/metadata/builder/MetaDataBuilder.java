/**
 *
 */
package org.mule.common.metadata.builder;

import org.mule.common.metadata.MetaDataModel;

public interface MetaDataBuilder<T extends MetaDataModel>
{

    T build();
}
