package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.constants.ParameterConstants;
import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.base.BaseDao;
import edu.epam.training.manager.dao.operations.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class TraineeDaoImpl extends BaseDao<Trainee, Long> implements TraineeDao {
    public TraineeDaoImpl(SessionFactory sessionFactory) {
        super(Trainee.class, sessionFactory);
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
