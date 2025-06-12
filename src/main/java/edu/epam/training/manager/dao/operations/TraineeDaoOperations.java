package edu.epam.training.manager.dao.operations;

import edu.epam.training.manager.dao.operations.helpers.TrainingQueryHelper;
import edu.epam.training.manager.domain.*;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.List;

public interface TraineeDaoOperations {
    SessionFactory getSessionFactory();

    default List<Training> getTraineeTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String trainerUsername,
                                               String trainingType) {

        return TrainingQueryHelper.fetchTrainings(
                getSessionFactory(),
                "trainee", username,
                "trainer", trainerUsername,
                trainingType,
                fromDate, toDate
        );
    }
}
