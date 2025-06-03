package edu.epam.training.manager.storage.common;

public interface StorageSelectableByUsername<T> {
    T findByUsername(String username);
}
