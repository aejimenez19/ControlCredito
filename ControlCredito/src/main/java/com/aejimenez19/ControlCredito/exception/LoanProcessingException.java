package com.aejimenez19.ControlCredito.exception;

public class LoanProcessingException extends RuntimeException {
    /**
     * Constructs a new loan processing exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public LoanProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new LoanProcessingException with the specified error message.
     *
     * @param message The detailed error message describing the cause of the exception
     */
    public LoanProcessingException(String message) {
        super(message);
    }

}
