package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;

import edu.epam.training.manager.service.CreateReadUpdateService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

@Setter
public class TrainerServiceImpl implements CreateReadUpdateService<Trainer, UUID> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    private TrainerDao<Trainer, UUID> trainerDAO;

    private UserService userService;
    private PasswordGenerator passwordGenerator;

    @Override
    public Trainer create(Trainer trainer) {
        LOGGER.debug("Starting trainer creation process for: {} {}",
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

        trainerDAO.create(newTrainer);

        LOGGER.debug("Trainer created successfully: {}", newTrainer);

        return newTrainer;
    }

    @Override
    public Trainer update(Trainer trainer) {
        LOGGER.debug("Starting trainer update process for ID: {}", trainer.getId());

        Trainer existing = findById(trainer.getId());

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

        Trainer result = trainerDAO.findById(trainer.getId())
                .orElseThrow(() -> {
                    LOGGER.error("Trainer with ID {} not found after update.", trainer.getId());
                    return new IllegalArgumentException("Trainer with ID " + trainer.getId() + " not found after update.");
                });

        LOGGER.debug("Trainer updated successfully with ID: {}", trainer.getId());

        return result;
    }

    @Override
    public Trainer findById(UUID id) {
        LOGGER.debug("Initiating search for trainer with ID: {}", id);

        Trainer trainer = trainerDAO.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Trainer with ID {} not found!", id);
                    return new IllegalArgumentException("Trainer with ID " + id + " not found.");
                });

        LOGGER.debug("Trainer found successfully: {}", trainer);

        return trainer;
    }
}
