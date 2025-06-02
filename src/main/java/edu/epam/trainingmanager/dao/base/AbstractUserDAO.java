package edu.epam.trainingmanager.dao.base;

import edu.epam.trainingmanager.dao.UserDAO;
import edu.epam.trainingmanager.domain.common.HasUsername;
import edu.epam.trainingmanager.domain.common.Identifiable;
import edu.epam.trainingmanager.storage.UserStorage;

import java.util.Optional;

public abstract class AbstractUserDAO<
        T extends Identifiable & HasUsername,
        S extends UserStorage<T>
        > extends AbstractDAO<T, S>
        implements UserDAO<T> {

    @Override
    public Optional<T> findByUsername(String username) {
        return Optional.ofNullable(storage.findByUsername(username));
    }
}

