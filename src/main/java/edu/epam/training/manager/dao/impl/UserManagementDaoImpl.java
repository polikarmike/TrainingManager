package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.UserManagementDao;
import edu.epam.training.manager.dao.HqlQueryConstants;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.base.DaoException;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Getter
public class UserManagementDaoImpl implements UserManagementDao {
    Logger LOGGER = LoggerFactory.getLogger(UserManagementDaoImpl.class);

    String LOG_VALIDATE_TEMPLATE   = "Validating credentials for username: {}";
    String LOG_ERROR_TEMPLATE      = "Error validating credentials for {} – {}";
    String ERROR_MSG_TEMPLATE      = "Error validating credentials for %s";

    String LOG_SEARCH_PREFIX       = "DAO: Searching usernames with prefix - {}";
    String LOG_SEARCH_PREFIX_ERROR = "DAO: Error fetching usernames by prefix {}: {}";
    String ERROR_SEARCH_PREFIX_TPL = "DAO: Error fetching usernames by prefix %s";

    private final SessionFactory sessionFactory;

    public UserManagementDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean validateCredentials(String username, String password) {
        LOGGER.debug(LOG_VALIDATE_TEMPLATE, username);

        try {
            Session session = sessionFactory.getCurrentSession();
            User user = session.createQuery(HqlQueryConstants.HQL_USER_VALIDATE, User.class)
                    .setParameter(ParameterConstants.USERNAME, username)
                    .setParameter(ParameterConstants.PASSWORD, password)
                    .uniqueResult();

            return user != null;
        } catch (Exception e) {
            String errorMsg = String.format(ERROR_MSG_TEMPLATE, username);
            LOGGER.error(LOG_ERROR_TEMPLATE, username, e.getMessage(), e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<String> findUsernamesWithPrefix(String prefix) {
        LOGGER.debug(LOG_SEARCH_PREFIX, prefix);

        try {
            Session session = sessionFactory.getCurrentSession();
            return session.createQuery(HqlQueryConstants.HQL_USER_FIND_USERNAMES_WITH_PREFIX, String.class)
                    .setParameter(ParameterConstants.PREFIX, prefix + "%")
                    .getResultList();

        } catch (Exception e) {
            LOGGER.error(LOG_SEARCH_PREFIX_ERROR, prefix, e.getMessage(), e);
            String errorMsg = String.format(ERROR_SEARCH_PREFIX_TPL, prefix);
            throw new DaoException(errorMsg, e);
        }
    }
}
