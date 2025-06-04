package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.base.BaseEntity;

public interface CrudService<T extends BaseEntity<ID>, ID> extends CreateReadUpdateService<T, ID> {
    void delete(ID id);
}
