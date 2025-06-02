package edu.epam.training.manager.storage.common;

import java.util.UUID;

public interface Selectable<T> {
    T findById(UUID id);
}

