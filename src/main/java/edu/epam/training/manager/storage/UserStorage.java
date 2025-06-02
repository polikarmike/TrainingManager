package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.storage.common.SelectableByUsername;

public interface UserStorage<T extends UserEntity> extends BaseStorage<T>, SelectableByUsername<T> {
    T findByUsername(String username);
}
