package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Training;

public interface TrainingService {
    Training addTraining(String authUsername, String authPassword, Training entity);
}
