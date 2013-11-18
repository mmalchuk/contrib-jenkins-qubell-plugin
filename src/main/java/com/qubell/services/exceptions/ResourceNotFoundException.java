package com.qubell.services.exceptions;

/**
 * Thrown when server returns 404
 * @author Alex Krupnov
 */
public class ResourceNotFoundException extends QubellServiceException {
    public ResourceNotFoundException() {
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
