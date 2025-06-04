package edu.epam.training.manager.dao;

import edu.epam.training.manager.domain.base.BaseEntity;

public interface CrudDao<T extends BaseEntity<ID>, ID> extends CreateReadUpdateDao<T, ID> {
    void delete(ID id);
}
