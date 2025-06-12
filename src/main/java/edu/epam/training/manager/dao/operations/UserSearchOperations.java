package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public interface UserSearchOperations {
    Logger LOGGER = LoggerFactory.getLogger(UserSearchOperations.class);

    String HQL_FIND_USERNAMES_WITH_PREFIX =
            "SELECT u.username FROM User u WHERE u.username LIKE :prefix";

    String LOG_SEARCH_PREFIX       = "DAO: Searching usernames with prefix - {}";
    String LOG_SEARCH_PREFIX_ERROR = "DAO: Error fetching usernames by prefix {}: {}";
    String ERROR_SEARCH_PREFIX_TPL = "DAO: Error fetching usernames by prefix %s";

    SessionFactory getSessionFactory();

    default List<String> findUsernamesWithPrefix(String prefix) {
        LOGGER.debug(LOG_SEARCH_PREFIX, prefix);

        try {
            Session session = getSessionFactory().getCurrentSession();
            return session.createQuery(HQL_FIND_USERNAMES_WITH_PREFIX, String.class)
                    .setParameter("prefix", prefix + "%")
                    .getResultList();

        } catch (Exception e) {
            LOGGER.error(LOG_SEARCH_PREFIX_ERROR, prefix, e.getMessage(), e);
            String errorMsg = String.format(ERROR_SEARCH_PREFIX_TPL, prefix);
            throw new DaoException(errorMsg, e);
        }
    }
}
