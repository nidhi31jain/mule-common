package org.mule.common.metadata.property.exception;

/**
 * Thrown when using {@link org.mule.common.metadata.MetaDataPropertyManager#MetaDataPropertyManager(java.util.List)}, the
 * collection passed as parameter, has repeated properties.
 * Please note that adding repeated properties will not trigger this exception, it is just for instantiation
 * scenarios only.
 */
public class RepeatedPropertyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception
     */
    public RepeatedPropertyException()
    {
    }

    /**
     * Create a new exception
     */
    public RepeatedPropertyException(String message)
    {
        super(message);
    }
}
