package org.mule.common.metadata.parser.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class JSONPointerType implements JSONType {

    private java.lang.String ref;
    private SchemaEnv env;

    public JSONPointerType(SchemaEnv env, java.lang.String reference) {
        this.ref = reference;
        this.env = env;
    }

    public JSONType resolve() throws SchemaException {

        java.lang.String[] splitRef = ref.split("#");

        if(splitRef.length==0){//Case "$ref"="#"
            return env.lookupType("#");
        }

        java.lang.String baseURI = splitRef[0];
        java.lang.String jsonPointer = splitRef[1];
        java.lang.String[] tokens = jsonPointer.split("/");

        if(tokens.length==0){//Case "$ref"=id + "#"
            return env.lookupType("#");
        }

        JSONType referenceType = null;

        // See if it has already been resolved if base URI is empty or matches this schema's id
        JSONObject contextJsonObject = env.getContextJsonObject();
        if(baseURI.equals("") || (contextJsonObject.has("id") && baseURI.equals(contextJsonObject.get("id")))){
            referenceType = env.lookupType(jsonPointer);
        }

        // If it has not, try to resolve it within the context document
        if (referenceType == null) {
            JSONObject jsonObjectToken = contextJsonObject;

            for (int i = 1; i < tokens.length; i++) {
                if (jsonObjectToken.has(tokens[i])) {
                    jsonObjectToken = (JSONObject) jsonObjectToken.get(tokens[i]);
                } else {
                    jsonObjectToken = null;
                    break;
                }
            }
            if (jsonObjectToken != null) {

                referenceType = env.evaluate(jsonObjectToken);
                env.addType(jsonPointer, referenceType);
                return referenceType;
            }
        } else {
            return referenceType;
        }

        // TODO: If it cannot find it within the context document try to look into files in the same directory
        if (referenceType == null) {

        }

        // If it cannot find it within the files try to get it on the internet or as a file:///
        if (referenceType == null) {
            URL url;
            try {
                url = new URL(baseURI);
                JSONObject remoteSchema = getRemoteSchema(url);
                return env.evaluate(remoteSchema);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public JSONObject getRemoteSchema(URL url) {

        HttpURLConnection conn;
        BufferedReader rd;
        java.lang.String line;
        java.lang.String result = "";
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();

            JSONObject refSchemaObject = new JSONObject(result);
            return refSchemaObject;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public java.lang.String explain(Object obj) {
        return null;
    }

    @Override
    public boolean isOptional() {
        return false;
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
        return true;
    }

}
