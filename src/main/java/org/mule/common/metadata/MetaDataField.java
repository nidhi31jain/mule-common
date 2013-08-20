package org.mule.common.metadata;

import java.util.List;

public interface MetaDataField {
	
	public String getName();

	public MetaDataModel getMetaDataModel();

	public FieldAccessType getAccessType();

    public List<Capability> getCapabilities();

    public String getImplementationClass();

    public void accept(CapabilityVisitor capabilityVisitor);

	public static enum FieldAccessType
	{
		READ, WRITE, READ_WRITE;
	}
}
