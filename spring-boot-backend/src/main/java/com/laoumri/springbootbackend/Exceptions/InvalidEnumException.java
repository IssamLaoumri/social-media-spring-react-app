package com.laoumri.springbootbackend.Exceptions;

public class InvalidEnumException extends RuntimeException {
    public InvalidEnumException(String message) {
        super(message);
    }
}
