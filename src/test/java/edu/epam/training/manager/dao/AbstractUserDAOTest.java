package edu.epam.training.manager.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import edu.epam.training.manager.dao.base.AbstractUserDAO;
import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.storage.UserStorage;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Data
class TestUser extends UserEntity {
    private final UUID id;
    private final String username;

    public TestUser(UUID id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TestUser{" + "id=" + id + ", username='" + username + '\'' + '}';
    }
}

interface TestUserStorage extends UserStorage<TestUser> { }

class TestUserDAO extends AbstractUserDAO<TestUser, TestUserStorage> { }

public class AbstractUserDAOTest {

    private TestUserDAO testUserDAO;
    private TestUserStorage testUserStorage;

    @BeforeEach
    void setUp() {
        testUserDAO = new TestUserDAO();
        testUserStorage = mock(TestUserStorage.class);
        testUserDAO.setStorage(testUserStorage);
    }

    @Test
    void testFindByUsername_success() {
        String username = "john.doe";
        UUID id = UUID.randomUUID();
        TestUser testUser = new TestUser(id, username);
        when(testUserStorage.findByUsername(username)).thenReturn(testUser);
        Optional<TestUser> result = testUserDAO.findByUsername(username);
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
        verify(testUserStorage).findByUsername(username);
    }

    @Test
    void testFindByUsername_notFound() {
        String username = "jane.doe";
        when(testUserStorage.findByUsername(username)).thenReturn(null);
        Optional<TestUser> result = testUserDAO.findByUsername(username);
        assertFalse(result.isPresent());
        verify(testUserStorage).findByUsername(username);
    }

    @Test
    void testFindByUsername_exception() {
        String username = "error.user";
        RuntimeException exception = new RuntimeException("findUser error");
        when(testUserStorage.findByUsername(username)).thenThrow(exception);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> testUserDAO.findByUsername(username));
        assertEquals("findUser error", thrown.getMessage());
        verify(testUserStorage).findByUsername(username);
    }
}
