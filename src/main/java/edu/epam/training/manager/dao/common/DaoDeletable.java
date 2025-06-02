package edu.epam.training.manager.dao.common;

import java.util.UUID;

public interface DaoDeletable<T> {
    void delete(UUID id);
}
