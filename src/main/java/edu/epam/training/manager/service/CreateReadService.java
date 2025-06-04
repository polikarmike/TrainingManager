package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.base.BaseEntity;

public interface CreateReadService<T extends BaseEntity<ID>, ID> {
    T create(T entity);
    T findById(ID id);
}
