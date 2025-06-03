package edu.epam.training.manager.service.common;

import java.util.UUID;

public interface ServiceReadable<T> {
    T select(UUID id);
}
