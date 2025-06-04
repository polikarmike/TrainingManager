package edu.epam.training.manager.dao.base;

import edu.epam.training.manager.dao.CreateReadUpdateDao;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Setter
public abstract class AbstractCreateReadUpdateDao<
        T extends BaseEntity<ID>,
        S extends BaseStorage<T, ID>,
        ID
        > extends AbstractCreateReadDao<T, S, ID>
        implements CreateReadUpdateDao<T, ID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCreateReadUpdateDao.class);

    @Override
    public void update(T item) {
        LOGGER.debug("{}: DAO UPDATE - Starting update for entity {}", item.getClass().getSimpleName(), item);
        try {
            getStorage().update(item);
            LOGGER.debug("{}: DAO UPDATE - Successfully updated entity with ID {}", item.getClass().getSimpleName(), item.getId());
        } catch (Exception e) {
            LOGGER.error("{}: DAO UPDATE - Error updating entity {}: {}", item.getClass().getSimpleName(), item, e.getMessage(), e);
            throw e;
        }
    }
}
