package edu.epam.trainingmanager.service.impl;


import edu.epam.trainingmanager.dao.UserDAO;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;

import edu.epam.trainingmanager.service.TrainerService;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.Predicate;

@Setter
public class TrainerServiceImpl implements TrainerService {
    @Autowired
    private UserDAO<Trainer> trainerDAO;
    @Autowired
    private UserDAO<Trainee> traineeDAO;

    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;
    private IdGenerator idGenerator;



    public Trainer createProfile(Trainer trainer) {
        String id = idGenerator.generate();
        String username = generateUniqueUsername(trainer.getFirstName(), trainer.getLastName());
        String password = passwordGenerator.generate(10);

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

    public Trainer updateProfile(Trainer trainer) {
        trainerDAO.update(trainer);
        return trainerDAO.findById(trainer.getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer with ID " + trainer.getId() + " not found after update."));
    }

    public Trainer getProfile(String id) {
        return trainerDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer with ID " + id + " not found."));
    }

    public List<Trainer> getAllProfiles() {
        return trainerDAO.findAll().stream().toList();
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        Predicate<String> usernameExists = username ->
                trainerDAO.findByUsername(username).isPresent() ||
                        traineeDAO.findByUsername(username).isPresent();

        return usernameGenerator.generate(firstName, lastName, usernameExists);
    }
}


