package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface DeleteDao<T extends BaseEntity<ID>, ID> {
    Logger LOGGER = LoggerFactory.getLogger(DeleteDao.class);

    String LOG_DELETE_START      = "{}: DAO DELETE - Starting deletion for item with ID {}";
    String LOG_DELETE_SUCCESS    = "{}: DAO DELETE - Successfully deleted item with ID {}";
    String LOG_DELETE_ERROR      = "{}: DAO DELETE - Error deleting item with ID {} – {}";
    String ERROR_DELETE_TEMPLATE = "%s: DAO DELETE - Error deleting item with ID %s";

    SessionFactory getSessionFactory();
    Class<T> getEntityClass();

    default void delete(ID id) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_DELETE_START, entityName, id);

        try {
            Session session = getSessionFactory().getCurrentSession();
            T entity = session.byId(getEntityClass()).load(id);
            session.remove(entity);

            LOGGER.debug(LOG_DELETE_SUCCESS, entityName, id);

        } catch (Exception e) {
            LOGGER.error(LOG_DELETE_ERROR, entityName, id, e.getMessage(), e);
            String errorMsg = String.format(ERROR_DELETE_TEMPLATE, entityName, id);
            throw new DaoException(errorMsg, e);
        }
    }
}
