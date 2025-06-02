package edu.epam.training.manager.storage.common;

import java.util.UUID;

public interface Deletable<T> {
    void delete(UUID id);
}
