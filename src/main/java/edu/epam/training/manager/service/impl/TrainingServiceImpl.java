package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.CreateReadDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.service.CreateReadService;
import edu.epam.training.manager.service.CreateReadUpdateService;
import edu.epam.training.manager.service.CrudService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Setter
public class TrainingServiceImpl implements CreateReadService<Training, UUID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private CreateReadDao<Training, UUID> trainingDAO;

    private CrudService<Trainee, UUID> traineeService;

    private CreateReadUpdateService<Trainer, UUID> trainerService;

    @Override
    public Training create(Training training) {
        LOGGER.debug("Starting training creation process for training: {}", training.getTrainingName());

        validateTraineeExists(training.getTraineeId());

        Trainer trainer = trainerService.findById(training.getTrainerId());

        validateTrainerSpecialization(trainer.getSpecialization(), training.getTrainingType());

        UUID id = UUID.randomUUID();

        Training newTraining = Training.builder()
                .id(id)
                .trainingName(training.getTrainingName())
                .trainingType(training.getTrainingType())
                .trainerId(training.getTrainerId())
                .traineeId(training.getTraineeId())
                .trainingDate(training.getTrainingDate())
                .trainingDuration(training.getTrainingDuration())
                .build();

        trainingDAO.create(newTraining);

        LOGGER.debug("Training created successfully: {}", newTraining);
        return newTraining;
    }

    @Override
    public Training findById(UUID id) {
        LOGGER.debug("Initiating search for training with ID: {}", id);

        Training training = trainingDAO.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Training with ID {} not found!", id);
                    return new IllegalArgumentException("Training with ID " + id + " not found.");
                });

        LOGGER.debug("Training found successfully: {}", training);
        return training;
    }

    private void validateTraineeExists(UUID traineeId) {
        LOGGER.debug("Validating existence of trainee with ID {}", traineeId);
        traineeService.findById(traineeId);
    }

    private void validateTrainerSpecialization(TrainingType trainerSpecialization, TrainingType requiredSpecialization) {
        if (!trainerSpecialization.equals(requiredSpecialization)) {
            LOGGER.error(
                    "Trainer specialization {} does not match required specialization {}.",
                    trainerSpecialization,
                    requiredSpecialization
            );
            throw new IllegalArgumentException(
                    "Trainer specialization " + trainerSpecialization + " does not match required specialization " + requiredSpecialization
            );
        }
    }
}
