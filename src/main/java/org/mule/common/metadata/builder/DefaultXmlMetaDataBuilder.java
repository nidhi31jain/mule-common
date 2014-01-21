package org.mule.common.metadata.builder;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.mule.common.metadata.DefaultXmlMetaDataModel;
import org.mule.common.metadata.XmlMetaDataModel;

public class DefaultXmlMetaDataBuilder<P extends MetaDataBuilder<?>> implements XmlMetaDataBuilder<P> {

	public String name;
	public String[] schemas;
	public InputStream[] schemasStream;
	public Charset encoding = Charset.forName("UTF-8");
	public String example;
	
	public DefaultXmlMetaDataBuilder(String name) {
		this.name = name;
	}
	
	@Override
	public XmlMetaDataModel build() {
		XmlMetaDataModel model = null;
		if (schemas != null) {
			model = new DefaultXmlMetaDataModel(Arrays.asList(schemas), name, encoding, example);
		} else if (schemasStream != null) {

			List<String> result = new ArrayList<String>();
	        for (InputStream schemaStream : schemasStream) {
	            result.add(getStringFromInputStream(schemaStream, encoding));
	        }

			model = new DefaultXmlMetaDataModel(result, name, encoding, example);
		}
		
		return model;
	}

	private static String getStringFromInputStream(InputStream is, Charset encoding) {
	    try
	    {
	        if(encoding != null)
	        {
	            return IOUtils.toString(is, encoding.toString());
	        }
	        else
	        {
	            return IOUtils.toString(is);
	        }
	    }
	    catch(IOException ex)
	    {
	        // This is not likely to happen as we are reading from memory (Strings)
	        throw new RuntimeException("Failed to turn input stream into string with encoding [" + encoding + "]", ex);
	    }

	}
	
	@Override
	public DefaultXmlMetaDataBuilder<P> addSchemaStringList(String... schemas) {
		this.schemas = schemas;
		this.schemasStream = null;
		return this;
	}
	
	@Override
	public DefaultXmlMetaDataBuilder<P> addSchemaStreamList(InputStream... schemaStreams) {
		this.schemasStream = schemaStreams;
		this.schemas = null;
		return this;
	}

	@Override
	public DefaultXmlMetaDataBuilder<P> setEncoding(Charset encoding) {
		this.encoding = encoding;
		return this;
	}

	@Override
	public DefaultXmlMetaDataBuilder<P> setExample(String xmlExample) {
		this.example = xmlExample;
		return this;
	}
}
