package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.UserManagementDao;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.exception.InvalidCredentialsException;
import edu.epam.training.manager.service.AuthenticationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserManagementDao userManagementDao;

    public AuthenticationServiceImpl(UserManagementDao userManagementDao) {
        this.userManagementDao = userManagementDao;
    }

    @Override
    @Transactional(readOnly = true)
    public void authenticateCredentials(Credentials credentials) {
        if (!userManagementDao.validateCredentials(credentials.username(), credentials.password())) {
            throw new InvalidCredentialsException(credentials.username());
        }
    }
}
