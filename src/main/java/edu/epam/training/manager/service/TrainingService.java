package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;

public interface TrainingService {
    Training addTraining(Credentials authCredentials, Training entity);
}
