package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.common.Creatable;
import edu.epam.training.manager.storage.common.Deletable;
import edu.epam.training.manager.storage.common.Selectable;
import edu.epam.training.manager.storage.common.Updatable;


public interface BaseStorage<T extends BaseEntity>
        extends Creatable<T>, Updatable<T>, Deletable<T>, Selectable<T> {
}
