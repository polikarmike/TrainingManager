package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.common.HasUser;
import edu.epam.training.manager.dao.operations.ReadDao;
import edu.epam.training.manager.dao.operations.UserAccountOperations;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.exception.base.ServiceException;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserAccountService<
        T extends BaseEntity<ID> & HasUser,
        ID,
        D extends UserAccountOperations<T, ID> & ReadDao<T, ID>
        > {

    Logger LOGGER = LoggerFactory.getLogger(UserAccountService.class);

    String LOG_SEARCH_BY_USERNAME_START   = "{}: SERVICE - Searching entity by username: {}";
    String LOG_SEARCH_BY_USERNAME_ERROR   = "{}: SERVICE ERROR - Entity with username '{}' not found";
    String LOG_SEARCH_BY_USERNAME_SUCCESS = "{}: SERVICE - Entity found by username: {}";

    String LOG_SEARCH_BY_ID_START         = "{}: SERVICE - Searching entity by ID: {}";
    String LOG_SEARCH_BY_ID_ERROR         = "{}: SERVICE ERROR - Entity with ID '{}' not found";
    String LOG_SEARCH_BY_ID_SUCCESS       = "{}: SERVICE - Entity found by ID: {}";

    String LOG_TOGGLE_STATUS_START        = "{}: SERVICE - Toggling active status for username: {}";
    String LOG_TOGGLE_STATUS_SUCCESS      = "{}: SERVICE - Active status toggled for username: {}";
    String LOG_TOGGLE_STATUS_ERROR        = "{}: SERVICE ERROR - Error toggling active status for username: {}: {}";

    String ERR_NOT_FOUND_BY_USERNAME      = "%s: Entity with username '%s' not found.";
    String ERR_NOT_FOUND_BY_ID            = "%s: Entity with ID '%s' not found.";
    String ERR_TOGGLE_STATUS              = "%s: Error toggling active status for '%s'";

    D getDao();
    AuthService getAuthService();
    PasswordGenerator getPasswordGenerator();

    T createProfile(T item);
    T updateProfile(String username, String password, T item);

    @Transactional(readOnly = true)
    default T findByUsername(String authUsername, String authPassword, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_SEARCH_BY_USERNAME_START, serviceName, username);

        getAuthService().authenticateCredentials(authUsername, authPassword);

        T item = getDao().findByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.error(LOG_SEARCH_BY_USERNAME_ERROR, serviceName, username);
                    String msg = String.format(ERR_NOT_FOUND_BY_USERNAME, serviceName, username);
                    return new EntityNotFoundException(msg);
                });

        LOGGER.debug(LOG_SEARCH_BY_USERNAME_SUCCESS, serviceName, username);
        return item;
    }

    @Transactional(readOnly = true)
    default T findById(ID id) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_SEARCH_BY_ID_START, serviceName, id);

        T item = getDao().findById(id)
                .orElseThrow(() -> {
                    LOGGER.error(LOG_SEARCH_BY_ID_ERROR, serviceName, id);
                    String msg = String.format(ERR_NOT_FOUND_BY_ID, serviceName, id);
                    return new EntityNotFoundException(msg);
                });

        LOGGER.debug(LOG_SEARCH_BY_ID_SUCCESS, serviceName, id);
        return item;
    }

    @Transactional
    default void toggleActiveStatus(String authUsername, String authPassword, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_TOGGLE_STATUS_START, serviceName, username);

        getAuthService().authenticateCredentials(authUsername, authPassword);
        T entity = findByUsername(authUsername, authPassword, username);

        User user = Optional.ofNullable(entity.getUser())
                .orElseThrow(() -> new EntityNotFoundException(serviceName + ": User entity is null for '" + username + "'."));

        user.setActive(!user.isActive());

        try {
            Session session = getDao().getSessionFactory().getCurrentSession();
            session.merge(user);
            LOGGER.debug(LOG_TOGGLE_STATUS_SUCCESS, serviceName, username);

        } catch (Exception e) {
            LOGGER.error(LOG_TOGGLE_STATUS_ERROR, serviceName, username, e.getMessage(), e);
            String msg = String.format(ERR_TOGGLE_STATUS, serviceName, username);
            throw new ServiceException(msg, e);
        }
    }

    @Transactional
    default String changePassword(String authUsername, String authPassword, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug("{}: SERVICE - Changing password for user: {}", serviceName, username);

        getAuthService().authenticateCredentials(authUsername, authPassword);
        T entity = findByUsername(authUsername, authPassword, username);

        User user = Optional.ofNullable(entity.getUser())
                .orElseThrow(() -> new EntityNotFoundException(serviceName + ": User entity is null for '" + username + "'."));

        String newPassword = getPasswordGenerator().generate();
        user.setPassword(newPassword);

        try {
            Session session = getDao().getSessionFactory().getCurrentSession();
            session.merge(user);
            LOGGER.debug("{}: SERVICE - Password changed for user: {}", serviceName, username);
            return newPassword;

        } catch (Exception e) {
            String msg = String.format("%s: Error changing password for user '%s'", serviceName, username);
            LOGGER.error("{}: {}", msg, e.getMessage(), e);
            throw new ServiceException(msg, e);
        }
    }
}
