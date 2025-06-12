package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainerService extends UserAccountService<Trainer, Long, TrainerDao>{
    List<Trainer> findUnassignedTrainers(String authUsername, String authPassword);
    List<Training> getTrainerTrainings(String authUsername,
                                       String authPassword,
                                       String username,
                                       LocalDate fromDate,
                                       LocalDate toDate,
                                       String trainerUsername);
}
