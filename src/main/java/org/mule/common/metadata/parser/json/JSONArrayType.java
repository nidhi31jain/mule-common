package org.mule.common.metadata.parser.json;

import static java.lang.String.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSONArrayType represents a schema type for a JSON array value.
 * 
 * The array type is characterized by the type of its items -- future versions may support optional cardinality constraints on the array as well.
 * 
 * @author Timothy Danford
 **/
public class JSONArrayType extends AbstractType { 

	private SchemaEnv env;
	private JSONType itemsType;
    private JSONObject contextDefinitionsJsonObject;

	public JSONArrayType(SchemaEnv schemaEnv, JSONObject schema, JSONObject contextDefinitionsJsonObject) throws SchemaException { 
		this.contextDefinitionsJsonObject = contextDefinitionsJsonObject;
	    
	    this.env = schemaEnv;
		itemsType = new JSONType.Everything();
		
		if(schema.has("items")) {
			try { 
				itemsType = env.evaluate(schema.get("items"));
			} catch(JSONException e) { 
				throw new SchemaException(e);
			}
		}
	}
	
    public JSONArrayType(SchemaEnv schemaEnv, JSONObject jsonObjectDefinition) throws SchemaException {
        this(schemaEnv, jsonObjectDefinition, null);
    }

    public JSONType getItemsType() {
        return itemsType;
    }

    public boolean contains(Object obj) { 
		if(obj == null || !(obj instanceof JSONArray)) { 
			return false; 
		}
		
		JSONArray array = (JSONArray)obj;
		for(int i = 0; i < array.length(); i++) { 
			try {
				if(!itemsType.contains(array.get(i))) { 
					return false;
				}
			} catch (JSONException e) {
				throw new IllegalArgumentException(array.toString());
			}
		}

		return true;
	}

	public java.lang.String explain(Object obj) {
		if(obj == null) { 
			return format("REJECT: null value");
		}
		
		if(!(obj instanceof JSONArray)) { 
			return format("REJECT: %s is type %s, not a JSONArray", obj.toString(),
					obj.getClass().getSimpleName());
		}
		
		JSONArray array = (JSONArray)obj;
		for(int i = 0; i < array.length(); i++) { 
			try {
				if(!itemsType.contains(array.get(i))) { 
					return itemsType.explain(array.get(i));
				}
			} catch (JSONException e) {
				throw new IllegalArgumentException(array.toString());
			}
		}		
		
		return null;
	}

    @Override
    public boolean isJSONPrimitive() {
        return false;
    }

    @Override
    public boolean isJSONArray() {
        return true;
    }

    @Override
    public boolean isJSONObject() {
        return false;
    }
}
