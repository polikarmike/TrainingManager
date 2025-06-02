package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.domain.Trainer;

import java.util.List;

public interface TrainerService {
    Trainer createProfile(Trainer trainer);
    Trainer updateProfile(Trainer trainer);
    Trainer getProfile(String id);
    List<Trainer> getAllProfiles();
}
