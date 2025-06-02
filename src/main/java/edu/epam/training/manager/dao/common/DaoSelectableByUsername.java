package edu.epam.training.manager.dao.common;

import java.util.Optional;

public interface DaoSelectableByUsername<T> {
    Optional<T> findByUsername(String username);
}
