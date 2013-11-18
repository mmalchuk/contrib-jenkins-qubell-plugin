package com.qubell.services.exceptions;

/**
 * Thrown when Qubell instance is busy for new requests
 * @author Alex Krupnov
 */
public class InstanceBusyException extends QubellServiceException {
    public InstanceBusyException() {
    }

    public InstanceBusyException(String message) {
        super(message);
    }

    public InstanceBusyException(String message, Throwable cause) {
        super(message, cause);
    }
}
