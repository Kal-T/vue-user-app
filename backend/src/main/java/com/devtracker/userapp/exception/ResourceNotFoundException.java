package com.devtracker.userapp.exception;

/**
 * Custom runtime exception thrown when requested database entity record is not found
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
