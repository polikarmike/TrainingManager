package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.operations.AuthenticationOperations;
import edu.epam.training.manager.exception.InvalidCredentialsException;
import edu.epam.training.manager.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceImplTest {

    private AuthenticationOperations authOperations;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        authOperations = mock(AuthenticationOperations.class);
        authService = new AuthServiceImpl(authOperations);
    }

    @Test
    void authenticateCredentials_validCredentials_noExceptionThrown() {
        String username = "validUser";
        String password = "correctPassword";

        when(authOperations.validateCredentials(username, password)).thenReturn(true);

        assertDoesNotThrow(() -> authService.authenticateCredentials(username, password));
        verify(authOperations).validateCredentials(username, password);
    }

    @Test
    void authenticateCredentials_invalidCredentials_exceptionThrown() {
        String username = "invalidUser";
        String password = "wrongPassword";

        when(authOperations.validateCredentials(username, password)).thenReturn(false);

        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () ->
                authService.authenticateCredentials(username, password));

        assertTrue(exception.getMessage().contains(username));
        verify(authOperations).validateCredentials(username, password);
    }
}
