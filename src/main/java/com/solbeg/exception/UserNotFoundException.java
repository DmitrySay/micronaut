package com.solbeg.exception;

import javax.inject.Singleton;

@Singleton
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
