package edu.epam.training.manager.service;

import edu.epam.training.manager.domain.Trainer;

import java.util.UUID;

public interface TrainerService {
    Trainer createProfile(Trainer trainer);
    Trainer updateProfile(Trainer trainer);
    Trainer getProfile(UUID id);
}
