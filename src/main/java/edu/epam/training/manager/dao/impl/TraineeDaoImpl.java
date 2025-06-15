package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.interfaces.TraineeDao;
import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.dao.interfaces.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.Trainee;
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
public class TraineeDaoImpl extends BaseDao<Trainee, Long> implements TraineeDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    private static final String LOG_DELETE_START      = "{}: DAO DELETE - Starting deletion for item with ID {}";
    private static final String LOG_DELETE_SUCCESS    = "{}: DAO DELETE - Successfully deleted item with ID {}";
    private static final String LOG_DELETE_ERROR      = "{}: DAO DELETE - Error deleting item with ID {} – {}";
    private static final String ERROR_DELETE_TEMPLATE = "%s: DAO DELETE - Error deleting item with ID %s";

    public TraineeDaoImpl(SessionFactory sessionFactory) {
        super(Trainee.class, sessionFactory);
    }

    public void delete(Long id) {
        String entityName = getEntityClass().getSimpleName();
        LOGGER.debug(LOG_DELETE_START, entityName, id);

        try {
            Session session = getSessionFactory().getCurrentSession();
            Trainee entity = session.byId(getEntityClass()).load(id);
            session.remove(entity);

            LOGGER.debug(LOG_DELETE_SUCCESS, entityName, id);

        } catch (Exception e) {
            LOGGER.error(LOG_DELETE_ERROR, entityName, id, e.getMessage(), e);
            String errorMsg = String.format(ERROR_DELETE_TEMPLATE, entityName, id);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<Training> getTraineeTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String trainerUsername,
                                               String trainingType) {

        return TrainingQueryHelper.fetchTrainings(
                getSessionFactory(),
                ParameterConstants.ROLE_TRAINEE, username,
                ParameterConstants.ROLE_TRAINER, trainerUsername,
                trainingType,
                fromDate, toDate
        );
    }
}
