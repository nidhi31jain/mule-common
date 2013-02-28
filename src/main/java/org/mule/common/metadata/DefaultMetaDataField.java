package org.mule.common.metadata;

public class DefaultMetaDataField 
	implements MetaDataField {

	private String name;
	private MetaDataModel model;
	
	public DefaultMetaDataField(final String name, final MetaDataModel model) {
		this.name = name;
		this.model = model;
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
