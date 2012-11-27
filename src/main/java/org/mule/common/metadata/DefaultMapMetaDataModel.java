package org.mule.common.metadata;

import java.util.Map;
import java.util.Set;

import org.mule.common.metadata.datatype.DataType;

public class DefaultMapMetaDataModel<K> extends DefaultMetaDataModel implements
		MapMetaDataModel<K> {
	
	private MetaDataModel keyMetaDataModel;
	private Map<K, ? extends MetaDataModel> metaDataModelMap;

	public DefaultMapMetaDataModel(MetaDataModel keyMetaDataModel, Map<K, ? extends MetaDataModel> metaDataModelMap) {
		super(DataType.MAP);
		this.keyMetaDataModel = keyMetaDataModel;
		this.metaDataModelMap = metaDataModelMap;
	}

	@Override
	public MetaDataModel getKeyModel() {
		return keyMetaDataModel;
	}

	@Override
	public Set<K> getKeys() {
		return metaDataModelMap.keySet();
	}

    @Override
	public MetaDataModel getValueModel(K key) {
		return metaDataModelMap.get(key);
	}

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((keyMetaDataModel == null) ? 0 : keyMetaDataModel.hashCode());
        result = prime * result + ((metaDataModelMap == null) ? 0 : metaDataModelMap.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (!(obj instanceof DefaultMapMetaDataModel)) return false;
        DefaultMapMetaDataModel other = (DefaultMapMetaDataModel) obj;
        if (keyMetaDataModel == null)
        {
            if (other.keyMetaDataModel != null) return false;
        }
        else if (!keyMetaDataModel.equals(other.keyMetaDataModel)) return false;
        if (metaDataModelMap == null)
        {
            if (other.metaDataModelMap != null) return false;
        }
        else if (!metaDataModelMap.equals(other.metaDataModelMap)) return false;
        return true;
    }

	@Override
	public String toString()
	{
	    StringBuilder sb = new StringBuilder();
	    sb.append("DefaultMapMetaDataModel:{");
	    sb.append(System.getProperty("line.separator"));
	    sb.append("  keyMetaDataModel:{ " + keyMetaDataModel.toString() + " }");
	    sb.append(System.getProperty("line.separator"));
	    sb.append("  metaDataModel:[");
	    sb.append(System.getProperty("line.separator"));
	    for (K key : getKeys()) {
	        sb.append("    { key:");
	        sb.append(key.toString());
	        sb.append(" value:");
	        sb.append(getValueModel(key).toString());
	        sb.append(" }");
	        sb.append(System.getProperty("line.separator"));
	    }
	    sb.append("  ]");
	    return sb.toString();
	}
}
