package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;

import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Setter
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private TrainerDao trainerDAO;

    private UserService userService;
    private PasswordGenerator passwordGenerator;

    @Override
    public Trainer createProfile(Trainer trainer) {
        UUID id = UUID.randomUUID();
        String username = userService.generateUniqueUsername(trainer.getFirstName(), trainer.getLastName());
        String password = passwordGenerator.generate();

        Trainer newTrainer = Trainer.builder()
                .id(id)
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .specialization(trainer.getSpecialization())
                .username(username)
                .password(password)
                .build();

        trainerDAO.create(newTrainer);
        return newTrainer;
    }

    @Override
    public Trainer updateProfile(Trainer trainer) {
        Trainer existing = getProfile(trainer.getId());

        Trainer updatedTrainer = Trainer.builder()
                .id(existing.getId())
                .firstName(Optional.ofNullable(trainer.getFirstName()).orElse(existing.getFirstName()))
                .lastName(Optional.ofNullable(trainer.getLastName()).orElse(existing.getLastName()))
                .username(existing.getUsername())
                .password(existing.getPassword())
                .isActive(Optional.of(trainer.isActive()).orElse(existing.isActive()))
                .specialization(Optional.ofNullable(trainer.getSpecialization()).orElse(existing.getSpecialization()))
                .build();

        trainerDAO.update(updatedTrainer);
        return trainerDAO.findById(trainer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer with ID " + trainer.getId() + " not found after update."));
    }

    @Override
    public Trainer getProfile(UUID id) {
        return trainerDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer with ID " + id + " not found."));
    }
}


