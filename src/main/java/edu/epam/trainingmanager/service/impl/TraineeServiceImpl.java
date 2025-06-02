package edu.epam.trainingmanager.service.impl;

import edu.epam.trainingmanager.dao.UserDAO;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.service.TraineeService;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;


import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.function.Predicate;

@Setter
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private UserDAO<Trainee> traineeDAO;
    @Autowired
    private UserDAO<Trainer> trainerDAO;

    private UsernameGenerator usernameGenerator;
    private PasswordGenerator passwordGenerator;
    private IdGenerator idGenerator;


    public Trainee createProfile(Trainee trainee) {
        String id = idGenerator.generate();
        String username = generateUniqueUsername(trainee.getFirstName(), trainee.getLastName());
        String password = passwordGenerator.generate(10);

        Trainee newTrainee = Trainee.builder()
                .id(id)
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .username(username)
                .password(password)
                .build();

        traineeDAO.create(newTrainee);
        return newTrainee;
    }

    public Trainee updateProfile(Trainee trainee) {
        traineeDAO.update(trainee);
        return traineeDAO.findById(trainee.getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainee with ID " + trainee.getId() + " not found after update."));
    }

    public void deleteProfile(String id) {
        traineeDAO.delete(id);
    }

    public Trainee getProfile(String id) {
        return traineeDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee with ID " + id + " not found."));
    }

    public List<Trainee> getAllProfiles() {
        return traineeDAO.findAll().stream().toList();
    }

    private String generateUniqueUsername(String firstName, String lastName) {
        Predicate<String> usernameExists = username ->
                traineeDAO.findByUsername(username).isPresent() ||
                        trainerDAO.findByUsername(username).isPresent();

        return usernameGenerator.generate(firstName, lastName, usernameExists);
    }
}

