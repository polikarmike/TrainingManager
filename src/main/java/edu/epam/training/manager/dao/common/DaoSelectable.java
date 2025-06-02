package edu.epam.training.manager.dao.common;

import java.util.Optional;
import java.util.UUID;

public interface DaoSelectable<T> {
    Optional<T> findById(UUID id);
}

