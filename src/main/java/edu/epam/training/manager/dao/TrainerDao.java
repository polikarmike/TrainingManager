package edu.epam.training.manager.dao;

import edu.epam.training.manager.domain.base.UserEntity;

import java.util.Optional;

public interface TrainerDao<T extends UserEntity<ID>, ID> extends CreateReadUpdateDao<T, ID>{
    Optional<T> findByUsername(String username);
}
