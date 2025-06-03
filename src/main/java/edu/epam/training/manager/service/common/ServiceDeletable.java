package edu.epam.training.manager.service.common;

import java.util.UUID;

public interface ServiceDeletable<T> {
    void delete(UUID id);
}
