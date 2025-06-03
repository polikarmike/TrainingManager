package edu.epam.training.manager.service.common;

public interface ServiceUpdatable<T> {
    T update(T entity);
}
