package com.dimathicc.ens.securityservice.exception;

public class UserJwtNotFoundException extends RuntimeException {
    public UserJwtNotFoundException(String message) {
        super(message);
    }
}
