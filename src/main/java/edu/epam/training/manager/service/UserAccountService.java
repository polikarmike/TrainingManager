package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.operations.CreateDao;
import edu.epam.training.manager.dao.operations.UpdateDao;
import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.dao.operations.ReadDao;
import edu.epam.training.manager.dao.operations.UserAccountOperations;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.exception.base.ServiceException;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UserAccountService<
        T extends UserEntity<ID>,
        ID,
        D extends UserAccountOperations<T, ID> & CreateDao<T, ID> & ReadDao<T, ID> & UpdateDao<T, ID>
        > {

    Logger LOGGER = LoggerFactory.getLogger(UserAccountService.class);

    String LOG_CREATE_START = "{}: SERVICE - Creating entity: {} {}";
    String LOG_CREATE_SUCCESS = "{}: SERVICE - Created entity: {}";

    String LOG_UPDATE_START = "{}: SERVICE - Updating entity ID: {}";
    String LOG_UPDATE_SUCCESS = "{}: SERVICE - Updated entity ID: {}";

    String LOG_SEARCH_BY_USERNAME_START   = "{}: SERVICE - Searching entity by username: {}";
    String LOG_SEARCH_BY_USERNAME_ERROR   = "{}: SERVICE ERROR - Entity with username '{}' not found";
    String LOG_SEARCH_BY_USERNAME_SUCCESS = "{}: SERVICE - Entity found by username: {}";

    String LOG_SEARCH_BY_ID_START         = "{}: SERVICE - Searching entity by ID: {}";
    String LOG_SEARCH_BY_ID_ERROR         = "{}: SERVICE ERROR - Entity with ID '{}' not found";
    String LOG_SEARCH_BY_ID_SUCCESS       = "{}: SERVICE - Entity found by ID: {}";

    String LOG_TOGGLE_STATUS_START        = "{}: SERVICE - Toggling active status for username: {}";
    String LOG_TOGGLE_STATUS_SUCCESS      = "{}: SERVICE - Active status toggled for username: {}";
    String LOG_TOGGLE_STATUS_ERROR        = "{}: SERVICE ERROR - Error toggling active status for username: {}: {}";

    String LOG_CHANGE_PASSWORD_START      = "{}: SERVICE - Changing password for user: {}";
    String LOG_CHANGE_PASSWORD_SUCCESS    = "{}: SERVICE - Password changed for user: {}";
    String LOG_CHANGE_PASSWORD_ERROR      = "{}: SERVICE ERROR - Error changing password for user: {}: {}";

    String ERR_NOT_FOUND_BY_USERNAME      = "%s: Entity with username '%s' not found.";
    String ERR_NOT_FOUND_BY_ID            = "%s: Entity with ID '%s' not found.";
    String ERR_TOGGLE_STATUS              = "%s: Error toggling active status for '%s'";
    String ERR_CHANGE_PASSWORD            = "%s: Error changing password for '%s'";

    D getDao();
    AuthenticationService getAuthService();
    PasswordGenerator getPasswordGenerator();
    UserService getUserService();

    T buildProfile(User user, T profile);
    void updateProfileSpecificFields(T existing, T item);

    @Transactional
    default T createProfile(T item) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_CREATE_START,
                serviceName,
                item.getUser().getFirstName(),
                item.getUser().getLastName()
        );

        String username = getUserService().generateUniqueUsername(
                item.getUser().getFirstName(),
                item.getUser().getLastName()
        );
        String password = getPasswordGenerator().generate();

        User newUser = User.builder()
                .firstName(item.getUser().getFirstName())
                .lastName(item.getUser().getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();

        T newEntity = buildProfile(newUser, item);
        T created = getDao().create(newEntity);

        LOGGER.debug(LOG_CREATE_SUCCESS, serviceName, created);
        return created;
    }

    @Transactional
    default T updateProfile(Credentials authCredentials, T item) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_UPDATE_START, serviceName, item.getId());

        getAuthService().authenticateCredentials(authCredentials);

        T existing = findById(authCredentials, item.getId());

        User existingUser = existing.getUser();
        User newUserData = item.getUser();

        Optional.ofNullable(newUserData.getFirstName()).ifPresent(existingUser::setFirstName);
        Optional.ofNullable(newUserData.getLastName()).ifPresent(existingUser::setLastName);
        existingUser.setActive(newUserData.isActive());

        updateProfileSpecificFields(existing, item);

        T updated = getDao().update(existing);
        LOGGER.debug(LOG_UPDATE_SUCCESS, serviceName, existing.getId());
        return updated;
    }

    @Transactional(readOnly = true)
    default T findByUsername(Credentials authCredentials, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_SEARCH_BY_USERNAME_START, serviceName, username);

        getAuthService().authenticateCredentials(authCredentials);

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
    default T findById(Credentials authCredentials, ID id) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_SEARCH_BY_ID_START, serviceName, id);

        getAuthService().authenticateCredentials(authCredentials);

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
    default void toggleActiveStatus(Credentials authCredentials, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_TOGGLE_STATUS_START, serviceName, username);

        getAuthService().authenticateCredentials(authCredentials);
        T entity = findByUsername(authCredentials, username);

        User user = entity.getUser();

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
    default String changePassword(Credentials authCredentials, String username) {
        String serviceName = getClass().getSimpleName();
        LOGGER.debug(LOG_CHANGE_PASSWORD_START, serviceName, username);

        getAuthService().authenticateCredentials(authCredentials);
        T entity = findByUsername(authCredentials, username);

        User user = entity.getUser();

        String newPassword = getPasswordGenerator().generate();
        user.setPassword(newPassword);

        try {
            Session session = getDao().getSessionFactory().getCurrentSession();
            session.merge(user);
            LOGGER.debug(LOG_CHANGE_PASSWORD_SUCCESS, serviceName, username);
            return newPassword;

        } catch (Exception e) {
            LOGGER.error(LOG_CHANGE_PASSWORD_ERROR, serviceName, username, e.getMessage(), e);

            throw new ServiceException(ERR_CHANGE_PASSWORD, e);
        }
    }
}
