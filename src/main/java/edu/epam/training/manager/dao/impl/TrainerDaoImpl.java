package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.dao.HqlQueryConstants;
import edu.epam.training.manager.dao.operations.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.exception.base.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TrainerDaoImpl extends BaseDao<Trainer, Long> implements TrainerDao {
    Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    String LOG_FIND_UNASSIGNED_START =
            "{}: DAO FETCH - Searching unassigned trainers";
    String LOG_FIND_UNASSIGNED_SUCCESS =
            "{}: DAO FETCH - Found {} unassigned trainers";
    String LOG_FIND_UNASSIGNED_ERROR =
            "{}: DAO ERROR - Error fetching unassigned trainers: {}";
    String ERROR_FIND_UNASSIGNED_ERROR =
            "DAO: Error fetching unassigned trainers";

    public TrainerDaoImpl(SessionFactory sessionFactory) {
        super(Trainer.class, sessionFactory);
    }


    @Override
    public List<Training> getTrainerTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String traineeUsername) {
        return TrainingQueryHelper.fetchTrainings(
                getSessionFactory(),
                ParameterConstants.ROLE_TRAINER, username,
                ParameterConstants.ROLE_TRAINEE, traineeUsername,
                null,
                fromDate, toDate
        );
    }

    @Override
    public List<Trainer> findUnassignedTrainers() {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_FIND_UNASSIGNED_START, entityName);

        try {
            Session session = getSessionFactory().getCurrentSession();
            List<Trainer> result = session.createQuery(HqlQueryConstants.HQL_TRAINER_FIND_UNASSIGNED, Trainer.class)
                    .getResultList();

            LOGGER.debug(LOG_FIND_UNASSIGNED_SUCCESS, entityName, result.size());
            return result;

        } catch (Exception e) {
            LOGGER.error(LOG_FIND_UNASSIGNED_ERROR, getEntityClass().getSimpleName(), e.getMessage(), e);
            throw new DaoException(ERROR_FIND_UNASSIGNED_ERROR, e);
        }
    }
}
