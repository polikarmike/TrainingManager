package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.TrainingService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Setter
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private TrainingDao trainingDAO;

    private TraineeServiceImpl traineeServiceImpl;
    private TrainerServiceImpl trainerServiceImpl;

    @Override
    public Training createTraining(Training training) {
        Trainee trainee = traineeServiceImpl.getProfile(training.getTraineeId());
        if (trainee == null) {
            throw new IllegalArgumentException("Trainee with ID " + training.getTraineeId() + " does not exist.");
        }

        Trainer trainer = trainerServiceImpl.getProfile(training.getTrainerId());
        if (trainer == null) {
            throw new IllegalArgumentException("Trainer with ID " + training.getTrainerId() + " does not exist.");
        }

        if (!trainer.getSpecialization().equals(training.getTrainingType())) {
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

        trainingDAO.create(newTraining);
        return newTraining;
    }

    @Override
    public Training getTraining(UUID id) {
        return trainingDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training with ID " + id + " not found."));
    }
}

