package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public interface ReadDao<T extends BaseEntity<ID>, ID> {
    Logger LOGGER = LoggerFactory.getLogger(ReadDao.class);

    String LOG_READ_START      = "{}: DAO READ - Searching for item with ID {}";
    String LOG_READ_ERROR      = "{}: DAO READ - Error searching for item with ID {} – {}";
    String ERROR_READ_TEMPLATE = "%s: DAO READ - Error searching for item with ID %s";

    SessionFactory getSessionFactory();
    Class<T> getEntityClass();

    default Optional<T> findById(ID id) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_READ_START, entityName, id);

        try {
            Session session = getSessionFactory().getCurrentSession();
            T result = session.get(getEntityClass(), id);

            return Optional.ofNullable(result);

        } catch (Exception e) {
            LOGGER.error(LOG_READ_ERROR, entityName, id, e.getMessage(), e);
            String errorMsg = String.format(ERROR_READ_TEMPLATE, entityName, id);
            throw new DaoException(errorMsg, e);
        }
    }
}
