package org.mule.common.metadata.exception;

/**
 * Thrown when a field does not know its implementation class
 */
public class NoImplementationClassException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Create a new exception
     */
    public NoImplementationClassException()
    {
    }

    /**
     * Create a new exception
     */
    public NoImplementationClassException(String message)
    {
        super(message);
    }
}
