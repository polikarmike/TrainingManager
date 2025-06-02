package edu.epam.trainingmanager.service.impl;

import edu.epam.trainingmanager.dao.BaseDAO;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.service.TrainingService;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Setter
public class TrainingServiceImpl implements TrainingService {
    @Autowired
    private BaseDAO<Training> trainingDAO;

    private IdGenerator idGenerator;
    private TraineeServiceImpl traineeServiceImpl;
    private TrainerServiceImpl trainerServiceImpl;


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

        String id = idGenerator.generate();

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

    public Training getTraining(String id) {
        return trainingDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training with ID " + id + " not found."));
    }

    public List<Training> getAllTrainings() {
        return trainingDAO.findAll().stream().toList();
    }

    public List<Training> findTrainingsByTrainer(String trainerId) {
        return trainingDAO.findAll().stream()
                .filter(training -> training.getTrainerId().equals(trainerId))
                .toList();
    }

    public List<Training> findTrainingsByTrainee(String traineeId) {
        return trainingDAO.findAll().stream()
                .filter(training -> training.getTraineeId().equals(traineeId))
                .toList();
    }
}

