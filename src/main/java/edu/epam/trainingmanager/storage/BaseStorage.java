package edu.epam.trainingmanager.storage;

import edu.epam.trainingmanager.domain.common.Identifiable;

import java.util.Collection;

public interface BaseStorage<T extends Identifiable> {
    void create(T item);
    T findById(String id);
    Collection<T> findAll();
    void update(T item);
    void delete(String id);
}
