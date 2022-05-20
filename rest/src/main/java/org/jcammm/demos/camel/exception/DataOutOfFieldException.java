package org.jcammm.demos.camel.exception;

public class DataOutOfFieldException extends Exception {

    public DataOutOfFieldException(String message) {
        super(message);
    }

    public DataOutOfFieldException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
