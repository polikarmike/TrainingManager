package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.TrainingService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Setter
public class TrainingServiceImpl implements TrainingService {
    private static final Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);

    @Autowired
    private TrainingDao trainingDAO;

    private TraineeServiceImpl traineeServiceImpl;
    private TrainerServiceImpl trainerServiceImpl;

    @Override
    public Training create(Training training) {
        logger.debug("Starting training creation process for training: {}", training.getTrainingName());

        try {
            traineeServiceImpl.select(training.getTraineeId());
        } catch (IllegalArgumentException e) {
            logger.error("Trainee with ID {} does not exist.", training.getTraineeId());
            throw new IllegalArgumentException("Trainee with ID " + training.getTraineeId() + " does not exist.");
        }

        Trainer trainer;
        try {
            trainer = trainerServiceImpl.select(training.getTrainerId());
        } catch (IllegalArgumentException e) {
            logger.error("Trainer with ID {} does not exist.", training.getTrainerId());
            throw new IllegalArgumentException("Trainer with ID " + training.getTrainerId() + " does not exist.");
        }

        if (!trainer.getSpecialization().equals(training.getTrainingType())) {
            logger.error("Trainer with ID {} does not specialize in {}.", trainer.getId(), training.getTrainingType());
            throw new IllegalArgumentException("Trainer " + trainer.getId() + " does not specialize in " + training.getTrainingType());
        }

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

        logger.warn("Attempting to save training in DAO with ID: {}", newTraining.getId());
        trainingDAO.create(newTraining);
        logger.debug("Training created successfully: {}", newTraining);

        return newTraining;
    }

    @Override
    public Training select(UUID id) {
        logger.debug("Initiating search for training with ID: {}", id);

        Training training = trainingDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Training with ID {} not found!", id);
                    return new IllegalArgumentException("Training with ID " + id + " not found.");
                });

        logger.debug("Training found successfully: {}", training);
        return training;
    }
}

