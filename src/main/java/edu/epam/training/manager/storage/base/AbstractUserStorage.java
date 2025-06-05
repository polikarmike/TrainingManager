package edu.epam.training.manager.storage.base;

import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.storage.UserStorage;

import java.util.Optional;

public abstract class AbstractUserStorage<T extends UserEntity<ID>, ID>
        extends AbstractStorage<T, ID> implements UserStorage<T, ID>
         {

    @Override
    public T findByUsername(String username) {
        Optional<T> result = storage.values()
                .stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
        return result.orElse(null);
    }
}
