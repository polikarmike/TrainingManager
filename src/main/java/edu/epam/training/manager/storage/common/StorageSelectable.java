package edu.epam.training.manager.storage.common;

import java.util.UUID;

public interface StorageSelectable<T> {
    T findById(UUID id);
}

