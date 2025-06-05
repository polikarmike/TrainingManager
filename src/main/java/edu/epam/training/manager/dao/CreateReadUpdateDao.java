package edu.epam.training.manager.dao;

import edu.epam.training.manager.domain.base.BaseEntity;

public interface CreateReadUpdateDao<T extends BaseEntity<ID>, ID> extends CreateReadDao<T, ID> {
    void update(T item);
}
