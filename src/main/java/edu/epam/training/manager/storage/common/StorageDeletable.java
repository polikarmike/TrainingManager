package edu.epam.training.manager.storage.common;

import java.util.UUID;

public interface StorageDeletable<T> {
    void delete(UUID id);
}
