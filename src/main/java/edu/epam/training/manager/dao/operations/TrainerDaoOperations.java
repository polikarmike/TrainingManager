package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.dao.operations.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.*;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

public interface TrainerDaoOperations {
    Logger LOGGER = LoggerFactory.getLogger(TrainerDaoOperations.class);

    String HQL_FIND_UNASSIGNED =
            "SELECT t FROM Trainer t WHERE t.trainees IS EMPTY";

    String LOG_FIND_UNASSIGNED_START =
            "{}: DAO FETCH - Searching unassigned trainers";
    String LOG_FIND_UNASSIGNED_SUCCESS =
            "{}: DAO FETCH - Found {} unassigned trainers";
    String LOG_FIND_UNASSIGNED_ERROR =
            "{}: DAO ERROR - Error fetching unassigned trainers: {}";

    SessionFactory getSessionFactory();
    Class<Trainer> getEntityClass();

    default List<Training> getTrainerTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String traineeUsername) {
        return TrainingQueryHelper.fetchTrainings(
                getSessionFactory(),
                "trainer", username,
                "trainee", traineeUsername,
                null,
                fromDate, toDate
        );
    }

    default List<Trainer> findUnassignedTrainers() {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_FIND_UNASSIGNED_START, entityName);
        try {
            Session session = getSessionFactory().getCurrentSession();
            List<Trainer> result = session.createQuery(HQL_FIND_UNASSIGNED, getEntityClass())
                    .getResultList();
            LOGGER.debug(LOG_FIND_UNASSIGNED_SUCCESS, entityName, result.size());
            return result;
        } catch (Exception e) {
            LOGGER.error(LOG_FIND_UNASSIGNED_ERROR, getEntityClass().getSimpleName(), e.getMessage(), e);
            throw new DaoException(
                    "Error fetching unassigned trainers", e
            );
        }
    }
}
