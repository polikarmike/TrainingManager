package edu.epam.training.manager.exception;

import edu.epam.training.manager.exception.base.ServiceException;

public class EntityNotFoundException extends ServiceException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
