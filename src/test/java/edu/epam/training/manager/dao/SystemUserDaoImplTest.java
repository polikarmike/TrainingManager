package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.impl.SystemUserDaoImpl;
import edu.epam.training.manager.dao.operations.AuthenticationOperations;
import edu.epam.training.manager.dao.operations.UserSearchOperations;
import edu.epam.training.manager.domain.User;

import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SystemUserDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<String> usernameQuery;

    @Mock
    private Query<User> authQuery;

    private SystemUserDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new SystemUserDaoImpl(sessionFactory);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testFindUsernamesWithPrefix_success() {
        String prefix = "adm";
        List<String> expectedUsernames = List.of("admin", "administrator");

        when(session.createQuery(UserSearchOperations.HQL_FIND_USERNAMES_WITH_PREFIX, String.class))
                .thenReturn(usernameQuery);
        when(usernameQuery.setParameter("prefix", prefix + "%"))
                .thenReturn(usernameQuery);
        when(usernameQuery.getResultList())
                .thenReturn(expectedUsernames);

        List<String> result = dao.findUsernamesWithPrefix(prefix);

        assertEquals(expectedUsernames, result);

        verify(session).createQuery(UserSearchOperations.HQL_FIND_USERNAMES_WITH_PREFIX, String.class);
        verify(usernameQuery).setParameter("prefix", prefix + "%");
        verify(usernameQuery).getResultList();
    }

    @Test
    void testFindUsernamesWithPrefix_throwsDaoException() {
        String prefix = "adm";

        when(session.createQuery(UserSearchOperations.HQL_FIND_USERNAMES_WITH_PREFIX, String.class))
                .thenThrow(new RuntimeException("DB error"));

        DaoException thrown = assertThrows(DaoException.class, () -> dao.findUsernamesWithPrefix(prefix));

        assertTrue(thrown.getMessage().contains(prefix));
    }

    @Test
    void testValidateCredentials_success() {
        String username = "user";
        String password = "pass";

        User user = new User();

        when(session.createQuery(AuthenticationOperations.HQL_VALIDATE, User.class))
                .thenReturn(authQuery);
        when(authQuery.setParameter("username", username))
                .thenReturn(authQuery);
        when(authQuery.setParameter("password", password))
                .thenReturn(authQuery);
        when(authQuery.uniqueResult())
                .thenReturn(user);

        boolean isValid = dao.validateCredentials(username, password);

        assertTrue(isValid);

        verify(session).createQuery(AuthenticationOperations.HQL_VALIDATE, User.class);
        verify(authQuery).setParameter("username", username);
        verify(authQuery).setParameter("password", password);
        verify(authQuery).uniqueResult();
    }

    @Test
    void testValidateCredentials_invalidCredentials() {
        String username = "user";
        String password = "wrongpass";

        when(session.createQuery(AuthenticationOperations.HQL_VALIDATE, User.class))
                .thenReturn(authQuery);
        when(authQuery.setParameter("username", username))
                .thenReturn(authQuery);
        when(authQuery.setParameter("password", password))
                .thenReturn(authQuery);
        when(authQuery.uniqueResult())
                .thenReturn(null);

        boolean isValid = dao.validateCredentials(username, password);

        assertFalse(isValid);
    }

    @Test
    void testValidateCredentials_throwsDaoException() {
        String username = "user";
        String password = "pass";

        when(session.createQuery(AuthenticationOperations.HQL_VALIDATE, User.class))
                .thenThrow(new RuntimeException("DB error"));

        DaoException thrown = assertThrows(DaoException.class, () -> dao.validateCredentials(username, password));

        assertTrue(thrown.getMessage().contains(username));
    }
}
