package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Training;

import java.util.UUID;

public interface TrainingService {
    Training createTraining(Training training);
    Training getTraining(UUID id);
}

