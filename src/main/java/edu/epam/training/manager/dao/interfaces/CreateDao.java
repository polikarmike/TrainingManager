package edu.epam.training.manager.dao.interfaces;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface CreateDao<T extends BaseEntity<ID>, ID> {
    Logger LOGGER = LoggerFactory.getLogger(CreateDao.class);

    String LOG_CREATE_START      = "{}: DAO CREATE - Starting creation new entity";
    String LOG_CREATE_SUCCESS    = "{}: DAO CREATE - Successfully created entity with ID {}";
    String LOG_CREATE_ERROR      = "{}: DAO CREATE - Error creating entity – {}";
    String ERROR_CREATE_TEMPLATE = "%s: DAO CREATE - Error creating entity";

    SessionFactory getSessionFactory();
    Class<T> getEntityClass();

    default T create(T item) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_CREATE_START, entityName);

        try {
            Session session = getSessionFactory().getCurrentSession();
            session.persist(item);
            session.flush();
            session.refresh(item);

            LOGGER.debug(LOG_CREATE_SUCCESS, entityName, item.getId());
            return item;

        } catch (Exception e) {
            LOGGER.error(LOG_CREATE_ERROR, entityName, e.getMessage(), e);
            String errorMsg = String.format(ERROR_CREATE_TEMPLATE, entityName);
            throw new DaoException(errorMsg, e);
        }
    }
}
