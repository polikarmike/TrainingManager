package edu.epam.training.manager.service;

import edu.epam.training.manager.dto.Credentials;

public interface AuthenticationService {
    void authenticateCredentials(Credentials credentials);
}
