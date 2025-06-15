package edu.epam.training.manager.exception;

import edu.epam.training.manager.exception.base.ServiceException;

public class InvalidStateException extends ServiceException {
    public InvalidStateException(String message) {
        super(message);
    }
}
