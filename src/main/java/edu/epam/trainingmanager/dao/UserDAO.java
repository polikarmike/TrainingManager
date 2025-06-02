package edu.epam.trainingmanager.dao;

import edu.epam.trainingmanager.domain.common.HasUsername;
import edu.epam.trainingmanager.domain.common.Identifiable;

import java.util.Optional;

public interface UserDAO<T extends Identifiable & HasUsername> extends BaseDAO<T> {
    Optional<T> findByUsername(String username);
}