package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Setter
public class TraineeServiceImpl implements TraineeService {
    @Autowired
    private TraineeDao traineeDAO;

    private UserService userService;
    private PasswordGenerator passwordGenerator;

    @Override
    public Trainee createProfile(Trainee trainee) {
        UUID id = UUID.randomUUID();
        String username = userService.generateUniqueUsername(trainee.getFirstName(), trainee.getLastName());
        String password = passwordGenerator.generate();

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

    @Override
    public Trainee updateProfile(Trainee traineeUpdate) {
        Trainee existing = getProfile(traineeUpdate.getId());

        Trainee updatedTrainee = Trainee.builder()
                .id(existing.getId())
                .firstName(Optional.ofNullable(traineeUpdate.getFirstName()).orElse(existing.getFirstName()))
                .lastName(Optional.ofNullable(traineeUpdate.getLastName()).orElse(existing.getLastName()))
                .username(existing.getUsername())
                .password(existing.getPassword())
                .isActive(Optional.of(traineeUpdate.isActive()).orElse(existing.isActive()))
                .dateOfBirth(Optional.ofNullable(traineeUpdate.getDateOfBirth()).orElse(existing.getDateOfBirth()))
                .address(Optional.ofNullable(traineeUpdate.getAddress()).orElse(existing.getAddress()))
                .build();

        traineeDAO.update(updatedTrainee);
        return traineeDAO.findById(traineeUpdate.getId())
                .orElseThrow(() -> new IllegalArgumentException("Trainee with ID " + traineeUpdate.getId() + " not found after update."));
    }

    @Override
    public void deleteProfile(UUID id) {
        traineeDAO.delete(id);
    }

    @Override
    public Trainee getProfile(UUID id) {
        return traineeDAO.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainee with ID " + id + " not found."));
    }
}

