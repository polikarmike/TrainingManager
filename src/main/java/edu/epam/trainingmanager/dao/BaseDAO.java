package edu.epam.trainingmanager.dao;

import edu.epam.trainingmanager.domain.common.Identifiable;

import java.util.Collection;
import java.util.Optional;

public interface BaseDAO<T extends Identifiable> {
    void create(T item);
    Optional<T> findById(String id);
    Collection<T> findAll();
    void update(T item);
    void delete(String id);
}
