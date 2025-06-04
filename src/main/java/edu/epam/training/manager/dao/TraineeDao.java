package edu.epam.training.manager.dao;

import edu.epam.training.manager.domain.base.UserEntity;

import java.util.Optional;

public interface TraineeDao<T extends UserEntity<ID>, ID> extends CrudDao<T, ID>{
    Optional<T> findByUsername(String username);
}
