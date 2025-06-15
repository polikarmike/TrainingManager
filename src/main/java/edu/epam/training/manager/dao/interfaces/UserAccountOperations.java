package edu.epam.training.manager.dao.interfaces;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.HqlQueryConstants;
import edu.epam.training.manager.domain.base.UserEntity;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public interface UserAccountOperations<T extends UserEntity<ID>, ID> {
    Logger LOGGER = LoggerFactory.getLogger(UserAccountOperations.class);

    String LOG_SEARCH_USERNAME           = "{}: DAO Searching by username - {}";
    String LOG_SEARCH_USERNAME_ERROR     = "{}: DAO Error fetching by username {}: {}";
    String ERROR_FETCH_USERNAME_TEMPLATE = "%s: DAO Error fetching by username %s";

    Class<T> getEntityClass();
    SessionFactory getSessionFactory();

    default Optional<T> findByUsername(String username) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_SEARCH_USERNAME, entityName, username);

        try {
            Session session = getSessionFactory().getCurrentSession();
            String hql = String.format(HqlQueryConstants.HQL_USER_FIND_BY_USERNAME, entityName);
            var query = session.createQuery(hql, getEntityClass())
                    .setParameter(ParameterConstants.USERNAME, username);

            return query.uniqueResultOptional();

        } catch (Exception e) {
            LOGGER.error(LOG_SEARCH_USERNAME_ERROR, entityName, username, e.getMessage(), e);
            throw new DaoException(String.format(ERROR_FETCH_USERNAME_TEMPLATE, entityName, username), e);
        }
    }
}
