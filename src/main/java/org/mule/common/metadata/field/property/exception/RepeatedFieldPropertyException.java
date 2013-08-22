package org.mule.common.metadata.field.property.exception;

/**
 * Thrown when for a given field, the same property's class is being added for a second time
 */
public class RepeatedFieldPropertyException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception
     */
    public RepeatedFieldPropertyException()
    {
    }

    /**
     * Create a new exception
     */
    public RepeatedFieldPropertyException(String message)
    {
        super(message);
    }
}
