package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.UserDAO;
import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.storage.UserStorage;

import java.util.Optional;

public abstract class AbstractUserDAO<
        T extends UserEntity,
        S extends UserStorage<T>
        > extends AbstractDAO<T, S>
        implements UserDAO<T> {

    @Override
    public Optional<T> findByUsername(String username) {
        return Optional.ofNullable(storage.findByUsername(username));
    }
}

