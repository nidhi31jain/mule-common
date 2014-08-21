package org.mule.common.metadata.parser.json;

public class SchemaException extends RuntimeException
{

    public SchemaException(String message)
    {
        super(message);
    }

    public SchemaException(Throwable cause)
    {
        super(cause);
    }

    public SchemaException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
