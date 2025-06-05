package edu.epam.training.manager.storage;

public interface BaseStorage<T, ID> {
    void create(T item);
    T findById(ID id);
    void update(T item);
    void delete(ID id);
}
