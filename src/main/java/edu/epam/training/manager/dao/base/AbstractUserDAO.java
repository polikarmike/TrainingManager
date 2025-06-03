package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.UserDAO;
import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public abstract class AbstractUserDAO<
        T extends UserEntity,
        S extends UserStorage<T>
        > extends AbstractDAO<T, S>
        implements UserDAO<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractUserDAO.class);

    @Override
    public Optional<T> findByUsername(String username) {
        logger.debug("DAO USER READ: Searching for user with username '{}'", username);
        T result;
        try {
            result = storage.findByUsername(username);
        } catch (Exception e) {
            logger.error("DAO USER READ: Error searching for user with username '{}': {}", username, e.getMessage(), e);
            throw e;
        }
        if (result == null) {
            logger.warn("DAO USER READ: No user found with username '{}'", username);
        } else {
            logger.debug("DAO USER READ: Found user: {}", result);
        }
        return Optional.ofNullable(result);
    }
}

