package edu.epam.trainingmanager.storage.base;

import edu.epam.trainingmanager.domain.common.HasUsername;
import edu.epam.trainingmanager.domain.common.Identifiable;
import edu.epam.trainingmanager.storage.UserStorage;

import java.util.Optional;

public abstract class AbstractUserStorage<T extends Identifiable & HasUsername>
        extends AbstractStorage<T>
        implements UserStorage<T> {

    @Override
    public T findByUsername(String username) {
        Optional<T> result = storage.values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
        return result.orElse(null);
    }
}
