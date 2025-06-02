package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.domain.Trainee;

import java.util.List;

public interface TraineeService {
    Trainee createProfile(Trainee trainee);
    Trainee updateProfile(Trainee trainee);
    void deleteProfile(String id);
    Trainee getProfile(String id);
    List<Trainee> getAllProfiles();
}
