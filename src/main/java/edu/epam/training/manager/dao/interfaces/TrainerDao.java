package edu.epam.training.manager.dao.interfaces;

import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainerDao
        extends CreateDao<Trainer, Long>,
                ReadDao<Trainer, Long>,
                UpdateDao<Trainer, Long>,
                UserAccountOperations<Trainer, Long> {

    List<Training> getTrainerTrainings(String username,
                                               LocalDate fromDate,
                                               LocalDate toDate,
                                               String traineeUsername);

    List<Trainer> findUnassignedTrainers();
}
