package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.domain.Training;

import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);
    Training getTraining(String id);
    List<Training> getAllTrainings();
    List<Training> findTrainingsByTrainer(String trainerId);
    List<Training> findTrainingsByTrainee(String traineeId);
}

