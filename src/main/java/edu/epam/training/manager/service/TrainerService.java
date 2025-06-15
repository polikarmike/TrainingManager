package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.interfaces.TrainerDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService extends UserAccountService<Trainer, Long, TrainerDao>{
    List<Trainer> findUnassignedTrainers(Credentials authCredentials);
    List<Training> getTrainerTrainings(Credentials authCredentials,
                                       String username,
                                       LocalDate fromDate,
                                       LocalDate toDate,
                                       String trainerUsername);
}
