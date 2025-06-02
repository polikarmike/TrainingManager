package edu.epam.trainingmanager.dao.base;

import edu.epam.trainingmanager.dao.BaseDAO;
import edu.epam.trainingmanager.domain.common.Identifiable;
import edu.epam.trainingmanager.storage.BaseStorage;
import lombok.Setter;

import java.util.Collection;
import java.util.Optional;

@Setter
public abstract class AbstractDAO<
        T extends Identifiable,
        S extends BaseStorage<T>
        > implements BaseDAO<T> {

    protected S storage;

    @Override
    public void create(T item) {
        storage.create(item);
    }

    @Override
    public Optional<T> findById(String id) {
        return Optional.ofNullable(storage.findById(id));
    }

    @Override
    public Collection<T> findAll() {
        return storage.findAll();
    }

    @Override
    public void update(T item) {
        storage.update(item);
    }

    @Override
    public void delete(String id) {
        storage.delete(id);
    }
}

