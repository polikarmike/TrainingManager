package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;

import java.time.LocalDate;
import java.util.List;

public interface TraineeService extends UserAccountService<Trainee, Long, TraineeDao>  {
    void delete(Credentials authCredentials, String username);

    List<Training> getTraineeTrainings(Credentials authCredentials,
                                       String username,
                                       LocalDate fromDate,
                                       LocalDate toDate,
                                       String trainerUsername,
                                       String trainingType);
}
