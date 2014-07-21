package org.mule.common.metadata.parser.json;

import java.io.*;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Lazy type loading for JSON schema files.  
 * 
 * Objects of this class correspond to files on the filesystem, each given by a {@link java.io.File},
 * which contain JSON Schema expressions.  However, the JSON schema expression is only loaded and parsed
 * the first time that either <tt>contains</tt> or <tt>explain</tt> is called on this object: <em>lazy</em> 
 * loading.  This is so that we can support resolution of mutually-recursive types, from files in a single
 * directory. 
 * 
 * @author Timothy Danford
 *
 */
public class JSONFileType extends AbstractType {
	
	private File file;
	private JSONType fileType;
	private SchemaEnv env;
	
	public JSONFileType(SchemaEnv env, File f) { 
		this.env = env;
		file = f;
		fileType = null;
	}

    public void loadType(File f) {
        try {
            FileReader reader = new FileReader(f);
            try {
                loadType(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            fileType = new JSONType.Empty();
            throw new IllegalArgumentException(file.getName(), e);
        }
    }

    public void loadType(Reader reader) {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[1024];
        int read = -1;
        try {
            while((read = reader.read(buffer)) != -1) {
                builder.append(buffer, 0, read);
            }

            java.lang.String jsonString = builder.toString();
            JSONObject obj = new JSONObject(jsonString);
            fileType = new JSONObjectType(env, obj);

        } catch (IOException e) {
            fileType = new JSONType.Empty();
            throw new IllegalArgumentException(file.getName(), e);

        } catch (JSONException e) {
            fileType = new JSONType.Empty();
            throw new IllegalArgumentException(file.getName(), e);

        } catch (SchemaException e) {
            fileType = new JSONType.Empty();
            throw new IllegalArgumentException(file.getName(), e);
        }
    }
	
	public void loadType() {
        loadType(file);
	}

	public boolean contains(Object obj) {
		if(fileType==null) { 
			loadType();
		}
		return fileType.contains(obj);
	}

	public java.lang.String explain(Object obj) {
		if(fileType==null) { loadType(); }
		return fileType.explain(obj);
	}

    @Override
    public boolean isJSONPrimitive() {
        return false;
    }

    @Override
    public boolean isJSONArray() {
        return false;
    }

    @Override
    public boolean isJSONObject() {
        return false;
    }

    @Override
    public boolean isJSONPointer() {
        return false;
    }
}
