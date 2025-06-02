package edu.epam.trainingmanager.storage;

import edu.epam.trainingmanager.domain.common.HasUsername;
import edu.epam.trainingmanager.domain.common.Identifiable;

public interface UserStorage<T extends Identifiable & HasUsername> extends BaseStorage<T> {
    T findByUsername(String username);
}
