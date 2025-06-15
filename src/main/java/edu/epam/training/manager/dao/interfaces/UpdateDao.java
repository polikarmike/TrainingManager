package edu.epam.training.manager.dao.interfaces;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface UpdateDao<T extends BaseEntity<ID>, ID> {
    Logger LOGGER = LoggerFactory.getLogger(UpdateDao.class);

    String LOG_UPDATE_START      = "{}: DAO UPDATE - Starting update for entity with ID {}";
    String LOG_UPDATE_SUCCESS    = "{}: DAO UPDATE - Successfully updated entity with ID {}";
    String LOG_UPDATE_ERROR      = "{}: DAO UPDATE - Error updating entity with ID {}: {}";
    String ERROR_UPDATE_TEMPLATE = "%s: DAO UPDATE - Error updating entity %s";

    SessionFactory getSessionFactory();
    Class<T> getEntityClass();

    default T update(T item) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_UPDATE_START, entityName, item.getId());

        try {
            Session session = getSessionFactory().getCurrentSession();
            session.merge(item);
            session.flush();
            session.refresh(item);

            LOGGER.debug(LOG_UPDATE_SUCCESS, entityName, item.getId());
            return item;

        } catch (Exception e) {
            LOGGER.error(LOG_UPDATE_ERROR, entityName, item, e.getMessage(), e);
            String errorMsg = String.format(ERROR_UPDATE_TEMPLATE, entityName, item.getId());
            throw new DaoException(errorMsg, e);
        }
    }
}
