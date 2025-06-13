package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.UserManagementDao;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.exception.InvalidCredentialsException;
import edu.epam.training.manager.service.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationServiceImplTest {

    private UserManagementDao userManagementDao;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        userManagementDao = mock(UserManagementDao.class);
        authenticationService = new AuthenticationServiceImpl(userManagementDao);
    }

    @Test
    void authenticateCredentials_validCredentials_noExceptionThrown() {
        String username = "validUser";
        String password = "correctPassword";

        Credentials credentials = new Credentials(username, password);

        when(userManagementDao.validateCredentials(username, password)).thenReturn(true);

        assertDoesNotThrow(() -> authenticationService.authenticateCredentials(credentials));
        verify(userManagementDao).validateCredentials(username, password);
    }

    @Test
    void authenticateCredentials_invalidCredentials_exceptionThrown() {
        String username = "invalidUser";
        String password = "wrongPassword";

        Credentials credentials = new Credentials(username, password);

        when(userManagementDao.validateCredentials(username, password)).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () ->
                authenticationService.authenticateCredentials(credentials));

        assertTrue(exception.getMessage().contains(username));
        verify(userManagementDao).validateCredentials(username, password);
    }
}
