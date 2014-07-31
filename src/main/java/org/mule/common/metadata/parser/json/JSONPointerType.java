package org.mule.common.metadata.parser.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class JSONPointerType implements JSONType {

    private java.lang.String ref;
    private SchemaEnv env;

    public JSONPointerType(SchemaEnv env, java.lang.String reference) {
        this.ref = reference;
        this.env = env;
    }

    public JSONType resolve(){

        java.lang.String[] splitRef = ref.split("#");
        java.lang.String[] tokens = null;

        try {

            if(splitRef.length==0){//Case "$ref"="#"
                return env.lookupType("#");
            }

            java.lang.String baseURI = splitRef[0];
            JSONType referenceType = null;

            if(splitRef.length>1){

                java.lang.String jsonPointer = splitRef[1];
                tokens = jsonPointer.split("/");

                if(tokens.length==0){//Case "$ref"=id + "#"
                    return env.lookupType("#");
                }


                // See if it has already been resolved if base URI is empty or matches this schema's id
                JSONObject contextJsonObject = env.getContextJsonObject();
                if(baseURI.equals("") || (contextJsonObject.has("id") && baseURI.equals(contextJsonObject.get("id")))){
                    referenceType = env.lookupType(jsonPointer);
                }

                // If it has not, try to resolve it within the context document
                if (referenceType == null) {
                    JSONObject jsonObjectToken = navigateJsonObject(tokens, contextJsonObject);
                    if (jsonObjectToken != null) {

                        referenceType = env.evaluate(jsonObjectToken);
                        env.addType(jsonPointer, referenceType);
                        return referenceType;
                    }
                } else {
                    return referenceType;
                }



                // TODO: If it cannot find it within the context document try to look into other loaded files



            }
            // If it cannot find it within the files try to get it on the internet or as a file:///
            if (referenceType == null) {
                URL url;
                JSONObject remoteSchema = null;
                try {
                    url = new URL(baseURI);
                    remoteSchema = getRemoteSchema(url);
                } catch (MalformedURLException e) {
                    //Try to get schema from a file in relative path
                    URL contextJsonURL = env.getContextJsonURL();
                    URL urlFile = new URL(contextJsonURL, baseURI);
                    remoteSchema = getRemoteSchema(urlFile);


                }
                if(tokens!=null && tokens.length>0){
                    JSONObject jsonObjectToken = navigateJsonObject(tokens, remoteSchema);
                    return env.evaluate(jsonObjectToken);
                }else{
                    return env.evaluate(remoteSchema);
                }


            }

        }catch (MalformedURLException e){
            throw new RuntimeException(e.getMessage());
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }catch (SchemaException e){
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }

    private JSONObject navigateJsonObject(java.lang.String[] tokens, JSONObject contextJsonObject) {
        JSONObject jsonObjectToken = contextJsonObject;

        for (int i = 1; i < tokens.length; i++) {
            if (jsonObjectToken.has(tokens[i])) {
                jsonObjectToken = (JSONObject) jsonObjectToken.get(tokens[i]);
            } else {
                jsonObjectToken = null;
                break;
            }
        }
        return jsonObjectToken;
    }

    public JSONObject getRemoteSchema(URL url) throws IOException {

        java.lang.String urlProtocol = url.getProtocol();
        if(urlProtocol.equals("file")){

            try {
                if(url.toURI().isAbsolute()) {
                    try {
                        java.lang.String fileSchemaString = IOUtils.toString(url.openStream());
                        JSONObject refSchemaObject = new JSONObject(fileSchemaString);
                        return refSchemaObject;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        } else if (urlProtocol.equals("http")){

            HttpURLConnection conn;
            BufferedReader rd;
            java.lang.String line;
            java.lang.String result = "";
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();

            JSONObject refSchemaObject = new JSONObject(result);
            return refSchemaObject;

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
