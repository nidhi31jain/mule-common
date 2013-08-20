package org.mule.common.metadata;

import java.util.ArrayList;
import java.util.List;

public class DefaultMetaDataField
	implements MetaDataField {

    private static String DEFAULT_IMPLEMENTATION_CLASS = "default";
	private String name;
	private MetaDataModel model;
	private FieldAccessType accessType;
    private List<Capability> capabilities;
    private String implementationClass = DEFAULT_IMPLEMENTATION_CLASS;
	
	public DefaultMetaDataField(final String name, final MetaDataModel model) {
		this(name, model, FieldAccessType.READ_WRITE, new ArrayList<Capability>(), model.getDataType().toString());
	}

    public DefaultMetaDataField(final String name, final MetaDataModel model, final FieldAccessType accessType) {
        this(name, model, accessType, new ArrayList<Capability>(), model.getDataType().toString());
    }

    public DefaultMetaDataField(final String name, final MetaDataModel model, List<Capability> capabilities, String implementationClass) {
        this(name, model, FieldAccessType.READ_WRITE, capabilities, implementationClass);
    }

	public DefaultMetaDataField(final String name, final MetaDataModel model, final FieldAccessType accessType, List<Capability> capabilities, String implementationClass) {
		this.name = name;
		this.model = model;
		this.accessType = accessType;
        this.capabilities = capabilities;
        this.implementationClass = implementationClass;
	}

	@Override
	public FieldAccessType getAccessType() {
		return accessType;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public MetaDataModel getMetaDataModel() {
		return model;
	}

    @Override
    public List<Capability> getCapabilities() {
        return this.capabilities;
    }

    @Override
    public String getImplementationClass() {
        //is default and we need to ask which is the class
        if (DEFAULT_IMPLEMENTATION_CLASS.equals(implementationClass)) {
            return model.getDefaultImplementationClass();
        } else {
            //it was overridden when created
            return implementationClass;
        }
    }

    @Override
    public void accept(CapabilityVisitor capabilityVisitor) {
        for(Capability capability : this.getCapabilities()){
            capability.accept(capabilityVisitor);
        }
    }

    @Override
	public String toString() {
		return "DefaultMetaDataField [name=" + name + ", model=" + model.getClass() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultMetaDataField other = (DefaultMetaDataField) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	

}
