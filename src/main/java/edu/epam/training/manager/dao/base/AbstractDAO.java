package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.BaseDAO;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Setter
public abstract class AbstractDAO<
        T extends BaseEntity,
        S extends BaseStorage<T>
        > implements BaseDAO<T> {

    protected S storage;

    @Override
    public void create(T item) {
        storage.create(item);
    }

    @Override
    public Optional<T> findById(UUID id) {
        return Optional.ofNullable(storage.findById(id));
    }

    @Override
    public void update(T item) {
        storage.update(item);
    }

    @Override
    public void delete(UUID id) {
        storage.delete(id);
    }
}

