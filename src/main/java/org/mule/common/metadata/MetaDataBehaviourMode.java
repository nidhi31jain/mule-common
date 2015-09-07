package org.mule.common.metadata;

/**
 * Defines if a metadata is either dynamic or static. Useful when Studio has to cache the structures within the catalog.
 */
public enum MetaDataBehaviourMode
{
    DYNAMIC, STATIC;
}
