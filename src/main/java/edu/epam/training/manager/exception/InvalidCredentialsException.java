package edu.epam.training.manager.exception;

import edu.epam.training.manager.exception.base.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {
    private static final String INVALID_CREDENTIALS_MSG = "Invalid credentials for user: %s";

    public InvalidCredentialsException(String username) {
        super(String.format(INVALID_CREDENTIALS_MSG, username));
    }
}
