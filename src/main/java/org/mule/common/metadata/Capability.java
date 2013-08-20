package org.mule.common.metadata;

/**
 */
public interface Capability {
    void accept(CapabilityVisitor capabilityVisitor);
}
