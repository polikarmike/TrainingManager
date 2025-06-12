package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface AuthenticationOperations {
    Logger LOGGER = LoggerFactory.getLogger(AuthenticationOperations.class);

    String HQL_VALIDATE =
            "FROM User u WHERE u.username = :username AND u.password = :password";

    String LOG_VALIDATE_TEMPLATE   = "Validating credentials for username: {}";
    String LOG_ERROR_TEMPLATE      = "Error validating credentials for {} – {}";
    String ERROR_MSG_TEMPLATE      = "Error validating credentials for %s";

    SessionFactory getSessionFactory();

    default boolean validateCredentials(String username, String password) {
        LOGGER.debug(LOG_VALIDATE_TEMPLATE, username);

        try {
            Session session = getSessionFactory().getCurrentSession();
            User user = session.createQuery(HQL_VALIDATE, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();

            return user != null;
        } catch (Exception e) {
            String errorMsg = String.format(ERROR_MSG_TEMPLATE, username);
            LOGGER.error(LOG_ERROR_TEMPLATE, username, e.getMessage(), e);
            throw new DaoException(errorMsg, e);
        }
    }
}
