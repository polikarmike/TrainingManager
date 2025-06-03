package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.common.StorageCreatable;
import edu.epam.training.manager.storage.common.StorageDeletable;
import edu.epam.training.manager.storage.common.StorageSelectable;
import edu.epam.training.manager.storage.common.StorageUpdatable;


public interface BaseStorage<T extends BaseEntity>
        extends StorageCreatable<T>, StorageUpdatable<T>, StorageDeletable<T>, StorageSelectable<T> {
}
