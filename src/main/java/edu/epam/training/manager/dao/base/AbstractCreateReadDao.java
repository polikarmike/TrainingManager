package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.CreateReadDao;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Setter
@Getter
public abstract class AbstractCreateReadDao<
        T extends BaseEntity<ID>,
        S extends BaseStorage<T, ID>,
        ID
        > implements CreateReadDao<T, ID> {

    private S storage;
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCreateReadDao.class);

    @Override
    public void create(T item) {
        LOGGER.debug("{}: DAO CREATE - Starting creation for entity {}", item.getClass().getSimpleName(), item);
        try {
            storage.create(item);
            LOGGER.debug("{}: DAO CREATE - Successfully created entity with ID {}", item.getClass().getSimpleName(), item.getId());
        } catch (Exception e) {
            LOGGER.error("{}: DAO CREATE - Error creating entity {}: {}", item.getClass().getSimpleName(), item, e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        LOGGER.debug("DAO READ: Searching for item with ID {}", id);
        T result;
        try {
            result = storage.findById(id);
        } catch (Exception e) {
            LOGGER.error("DAO READ: Error searching for item with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }

        return Optional.ofNullable(result);
    }
}
