package com.laoumri.springbootbackend.Exceptions;

public class EmailAddressAlreadyExists extends RuntimeException {
    public EmailAddressAlreadyExists() {
        super("Email Address Already Exists. Login instead.");
    }
}
