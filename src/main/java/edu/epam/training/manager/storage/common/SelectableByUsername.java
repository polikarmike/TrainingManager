package edu.epam.training.manager.storage.common;

public interface SelectableByUsername<T> {
    T findByUsername(String username);
}
