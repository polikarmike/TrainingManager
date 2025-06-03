package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;

import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Setter
public class TrainerServiceImpl implements TrainerService {
    private static final Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    private TrainerDao trainerDAO;

    private UserService userService;
    private PasswordGenerator passwordGenerator;

    @Override
    public Trainer create(Trainer trainer) {
        logger.debug("Starting trainer creation process for: {} {}",
                trainer.getFirstName(), trainer.getLastName());

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

        logger.warn("Attempting to register trainer in DAO with ID: {}", newTrainer.getId());

        trainerDAO.create(newTrainer);

        logger.debug("Trainer created successfully: {}", newTrainer);

        return newTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        logger.debug("Starting trainer update process for ID: {}", trainer.getId());

        Trainer existing = select(trainer.getId());

        Trainer updatedTrainer = Trainer.builder()
                .id(existing.getId())
                .firstName(Optional.ofNullable(trainer.getFirstName()).orElse(existing.getFirstName()))
                .lastName(Optional.ofNullable(trainer.getLastName()).orElse(existing.getLastName()))
                .username(existing.getUsername())
                .password(existing.getPassword())
                .isActive(Optional.of(trainer.isActive()).orElse(existing.isActive()))
                .specialization(Optional.ofNullable(trainer.getSpecialization()).orElse(existing.getSpecialization()))
                .build();

        logger.warn("Attempting to update trainer in DAO with ID: {}", trainer.getId());
        trainerDAO.update(updatedTrainer);

        Trainer result = trainerDAO.findById(trainer.getId())
                .orElseThrow(() -> {
                    logger.error("Trainer with ID {} not found after update.", trainer.getId());
                    return new IllegalArgumentException("Trainer with ID " + trainer.getId() + " not found after update.");
                });

        logger.debug("Trainer updated successfully with ID: {}", trainer.getId());
        return result;
    }

    @Override
    public Trainer select(UUID id) {
        logger.debug("Initiating search for trainer with ID: {}", id);

        Trainer trainer = trainerDAO.findById(id)
                .orElseThrow(() -> {
                    logger.error("Trainer with ID {} not found!", id);
                    return new IllegalArgumentException("Trainer with ID " + id + " not found.");
                });

        logger.debug("Trainer found successfully: {}", trainer);
        return trainer;
    }
}


