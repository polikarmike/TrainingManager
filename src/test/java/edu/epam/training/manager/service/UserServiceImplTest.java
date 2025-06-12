package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.operations.UserSearchOperations;
import edu.epam.training.manager.exception.InvalidStateException;
import edu.epam.training.manager.service.impl.UserServiceImpl;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserSearchOperations userSearchOperations;
    private UsernameGenerator usernameGenerator;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userSearchOperations = mock(UserSearchOperations.class);
        usernameGenerator = mock(UsernameGenerator.class);
        userService = new UserServiceImpl(userSearchOperations, usernameGenerator);
    }

    @Test
    void generateUniqueUsername_success() {
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = "jdoe";

        when(usernameGenerator.generateBaseUsername(firstName, lastName)).thenReturn(baseUsername);
        when(userSearchOperations.findUsernamesWithPrefix(baseUsername)).thenReturn(List.of("jdoe", "jdoe1"));
        when(usernameGenerator.generateCandidates(eq(baseUsername), anyInt()))
                .thenReturn(List.of("jdoe", "jdoe1", "jdoe2", "jdoe3"));

        String result = userService.generateUniqueUsername(firstName, lastName);

        assertEquals("jdoe2", result);
        verify(usernameGenerator).generateBaseUsername(firstName, lastName);
        verify(userSearchOperations).findUsernamesWithPrefix(baseUsername);
        verify(usernameGenerator).generateCandidates(baseUsername, 250);
    }

    @Test
    void generateUniqueUsername_failsAfterMaxAttempts() {
        String firstName = "Jane";
        String lastName = "Smith";
        String baseUsername = "jsmith";

        List<String> allTaken = List.of("jsmith", "jsmith1", "jsmith2");

        when(usernameGenerator.generateBaseUsername(firstName, lastName)).thenReturn(baseUsername);
        when(userSearchOperations.findUsernamesWithPrefix(baseUsername)).thenReturn(allTaken);
        when(usernameGenerator.generateCandidates(eq(baseUsername), anyInt()))
                .thenReturn(allTaken);

        InvalidStateException exception = assertThrows(InvalidStateException.class, () ->
                userService.generateUniqueUsername(firstName, lastName)
        );

        assertTrue(exception.getMessage().contains("Could not generate unique username"));
        verify(usernameGenerator).generateBaseUsername(firstName, lastName);
        verify(userSearchOperations).findUsernamesWithPrefix(baseUsername);
        verify(usernameGenerator).generateCandidates(baseUsername, 250);
    }
}
