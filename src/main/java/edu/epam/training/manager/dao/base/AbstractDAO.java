package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.BaseDAO;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

@Setter
public abstract class AbstractDAO<
        T extends BaseEntity,
        S extends BaseStorage<T>
        > implements BaseDAO<T> {

    protected S storage;

    private static final Logger logger = LoggerFactory.getLogger(AbstractDAO.class);

    @Override
    public void create(T item) {
        logger.debug("DAO CREATE: Starting creation for item with ID {}", item.getId());
        try {
            storage.create(item);
            logger.debug("DAO CREATE: Successfully created item with ID {}", item.getId());
        } catch (Exception e) {
            logger.error("DAO CREATE: Error creating item with ID {}: {}", item.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Optional<T> findById(UUID id) {
        logger.debug("DAO READ: Searching for item with ID {}", id);
        T result;
        try {
            result = storage.findById(id);
        } catch (Exception e) {
            logger.error("DAO READ: Error searching for item with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
        if (result == null) {
            logger.warn("DAO READ: No item found with ID {}", id);
        } else {
            logger.debug("DAO READ: Found item with ID {}: {}", id, result);
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void update(T item) {
        logger.debug("DAO UPDATE: Starting update for item with ID {}", item.getId());
        try {
            storage.update(item);
            logger.debug("DAO UPDATE: Successfully updated item with ID {}", item.getId());
        } catch (Exception e) {
            logger.error("DAO UPDATE: Error updating item with ID {}: {}", item.getId(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(UUID id) {
        logger.debug("DAO DELETE: Starting deletion for item with ID {}", id);
        try {
            storage.delete(id);
            logger.debug("DAO DELETE: Successfully deleted item with ID {}", id);
        } catch (Exception e) {
            logger.error("DAO DELETE: Error deleting item with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}

