package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.base.BaseEntity;

public interface CreateReadUpdateService<T extends BaseEntity<ID>, ID> extends CreateReadService<T, ID>{
    T update(T entity);
}
