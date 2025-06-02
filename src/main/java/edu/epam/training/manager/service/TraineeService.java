package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Trainee;

import java.util.UUID;

public interface TraineeService {
    Trainee createProfile(Trainee trainee);
    Trainee updateProfile(Trainee trainee);
    void deleteProfile(UUID id);
    Trainee getProfile(UUID id);
}
