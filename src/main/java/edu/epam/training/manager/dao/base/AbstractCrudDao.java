package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.CrudDao;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public abstract class AbstractCrudDao<
        T extends BaseEntity<ID>,
        S extends BaseStorage<T, ID>,
        ID
        > extends AbstractCreateReadUpdateDao<T, S, ID> implements CrudDao<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCrudDao.class);

    @Override
    public void delete(ID id) {
        LOGGER.debug("DAO DELETE: Starting deletion for item with ID {}", id);
        try {
            getStorage().delete(id);
            LOGGER.debug("DAO DELETE: Successfully deleted item with ID {}", id);
        } catch (Exception e) {
            LOGGER.error("DAO DELETE: Error deleting item with ID {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
