package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.service.CrudService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Setter
public class TraineeServiceImpl implements CrudService<Trainee, UUID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeDao<Trainee, UUID> traineeDAO;

    private UserService userService;
    private PasswordGenerator passwordGenerator;

    @Override
    public Trainee create(Trainee trainee) {
        LOGGER.debug("Starting trainee creation process for: {} {}", trainee.getFirstName(), trainee.getLastName());

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

        LOGGER.debug("Trainee created successfully: {}", newTrainee);

        return newTrainee;
    }

    @Override
    public Trainee update(Trainee traineeUpdate) {
        LOGGER.debug("Starting trainee update process for ID: {}",traineeUpdate.getId());

        Trainee existing = findById(traineeUpdate.getId());

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

        Trainee result = traineeDAO.findById(traineeUpdate.getId())
                .orElseThrow(() -> {
                    LOGGER.error("Trainee with ID {} not found after update.", traineeUpdate.getId());
                    return new IllegalArgumentException("Trainee with ID " + traineeUpdate.getId() + " not found after update.");
                });

        LOGGER.debug("Trainee updated successfully with ID: {}", traineeUpdate.getId());
        return result;
    }

    @Override
    public void delete(UUID id) {
        LOGGER.debug("Initiating deletion process for trainee with ID: {}", id);

        traineeDAO.delete(id);

        LOGGER.debug("Trainee with ID {} deleted successfully.", id);
    }

    @Override
    public Trainee findById(UUID id) {
        LOGGER.debug("Initiating search for trainee with ID: {}", id);

        Trainee trainee = traineeDAO.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Trainee with ID {} not found!", id);
                    return new IllegalArgumentException("Trainee with ID " + id + " not found.");
                });

        LOGGER.debug("Trainee found successfully: {}", trainee);
        return trainee;
    }
}
