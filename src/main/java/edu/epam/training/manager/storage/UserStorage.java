package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.base.UserEntity;

public interface UserStorage<T extends UserEntity<ID>, ID> extends BaseStorage<T, ID> {
    T findByUsername(String username);
}
