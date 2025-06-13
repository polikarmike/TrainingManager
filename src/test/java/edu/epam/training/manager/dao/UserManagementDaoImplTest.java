package edu.epam.training.manager.dao;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.impl.UserManagementDaoImpl;
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

class UserManagementDaoImplTest {

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private Query<String> usernameQuery;

    @Mock
    private Query<User> authQuery;

    private UserManagementDaoImpl dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new UserManagementDaoImpl(sessionFactory);

        when(sessionFactory.getCurrentSession()).thenReturn(session);
    }

    @Test
    void testFindUsernamesWithPrefix_success() {
        String prefix = "adm";
        List<String> expectedUsernames = List.of("admin", "administrator");

        when(session.createQuery(HqlQueryConstants.HQL_USER_FIND_USERNAMES_WITH_PREFIX, String.class))
                .thenReturn(usernameQuery);
        when(usernameQuery.setParameter(ParameterConstants.PREFIX, prefix + "%"))
                .thenReturn(usernameQuery);
        when(usernameQuery.getResultList())
                .thenReturn(expectedUsernames);

        List<String> result = dao.findUsernamesWithPrefix(prefix);

        assertEquals(expectedUsernames, result);

        verify(session).createQuery(HqlQueryConstants.HQL_USER_FIND_USERNAMES_WITH_PREFIX, String.class);
        verify(usernameQuery).setParameter(ParameterConstants.PREFIX, prefix + "%");
        verify(usernameQuery).getResultList();
    }

    @Test
    void testFindUsernamesWithPrefix_throwsDaoException() {
        String prefix = "adm";

        when(session.createQuery(HqlQueryConstants.HQL_USER_FIND_USERNAMES_WITH_PREFIX, String.class))
                .thenThrow(new RuntimeException("DB error"));

        DaoException thrown = assertThrows(DaoException.class, () -> dao.findUsernamesWithPrefix(prefix));

        assertTrue(thrown.getMessage().contains(prefix));
    }

    @Test
    void testValidateCredentials_success() {
        String username = "user";
        String password = "pass";

        User user = new User();

        when(session.createQuery(HqlQueryConstants.HQL_USER_VALIDATE, User.class))
                .thenReturn(authQuery);
        when(authQuery.setParameter(ParameterConstants.USERNAME, username))
                .thenReturn(authQuery);
        when(authQuery.setParameter(ParameterConstants.PASSWORD, password))
                .thenReturn(authQuery);
        when(authQuery.uniqueResult())
                .thenReturn(user);

        boolean isValid = dao.validateCredentials(username, password);

        assertTrue(isValid);

        verify(session).createQuery(HqlQueryConstants.HQL_USER_VALIDATE, User.class);
        verify(authQuery).setParameter(ParameterConstants.USERNAME, username);
        verify(authQuery).setParameter(ParameterConstants.PASSWORD, password);
        verify(authQuery).uniqueResult();
    }

    @Test
    void testValidateCredentials_invalidCredentials() {
        String username = "user";
        String password = "wrongpass";

        when(session.createQuery(HqlQueryConstants.HQL_USER_VALIDATE, User.class))
                .thenReturn(authQuery);
        when(authQuery.setParameter(ParameterConstants.USERNAME, username))
                .thenReturn(authQuery);
        when(authQuery.setParameter(ParameterConstants.PASSWORD, password))
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

        when(session.createQuery(HqlQueryConstants.HQL_USER_VALIDATE, User.class))
                .thenThrow(new RuntimeException("DB error"));

        DaoException thrown = assertThrows(DaoException.class, () -> dao.validateCredentials(username, password));

        assertTrue(thrown.getMessage().contains(username));
    }
}
