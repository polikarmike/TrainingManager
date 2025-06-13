package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.operations.*;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;

import java.time.LocalDate;
import java.util.List;

public interface TraineeDao
        extends CreateDao<Trainee, Long>,
                ReadDao<Trainee, Long>,
                UpdateDao<Trainee, Long>,
                DeleteDao<Trainee, Long>,
                UserAccountOperations<Trainee, Long>{

    List<Training> getTraineeTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String trainerUsername,
                                               String trainingType);
}
