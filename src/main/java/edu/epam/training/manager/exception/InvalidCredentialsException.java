package edu.epam.training.manager.exception;

import edu.epam.training.manager.exception.base.AuthenticationException;

public class InvalidCredentialsException extends AuthenticationException {
    public InvalidCredentialsException(String username) {
        super("Invalid credentials for user: " + username);
    }
}
