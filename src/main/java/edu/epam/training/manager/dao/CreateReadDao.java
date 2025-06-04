package edu.epam.training.manager.dao;

import edu.epam.training.manager.domain.base.BaseEntity;

import java.util.Optional;

public interface CreateReadDao<T extends BaseEntity<ID>, ID> {
    void create(T item);
    Optional<T> findById(ID id);
}
