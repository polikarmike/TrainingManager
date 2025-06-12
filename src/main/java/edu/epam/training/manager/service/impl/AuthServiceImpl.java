package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.operations.AuthenticationOperations;
import edu.epam.training.manager.exception.InvalidCredentialsException;
import edu.epam.training.manager.service.AuthService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationOperations authOperations;

    public AuthServiceImpl(AuthenticationOperations authOperations) {
        this.authOperations = authOperations;
    }

    @Override
    @Transactional(readOnly = true)
    public void authenticateCredentials(String authUsername, String authPassword) {
        if (!authOperations.validateCredentials(authUsername, authPassword)) {
            throw new InvalidCredentialsException(authUsername);
        }
    }
}
