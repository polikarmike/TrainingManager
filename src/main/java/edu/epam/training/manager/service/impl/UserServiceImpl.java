package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.UsernameGenerator;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Setter
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private TraineeDao<Trainee, UUID> traineeDAO;

    @Autowired
    private TrainerDao<Trainer, UUID> trainerDAO;

    private UsernameGenerator usernameGenerator;

    @Override
    public String generateUniqueUsername(String firstName, String lastName) {
        LOGGER.debug("Starting username generation for: firstName={}, lastName={}", firstName, lastName);

        return usernameGenerator.generateCandidates(firstName, lastName)
                .peek(candidate -> LOGGER.debug("Generated candidate username: {}", candidate))
                .filter(this::isUsernameUnique)
                .peek(uniqueCandidate -> LOGGER.debug("Username '{}' passed uniqueness check", uniqueCandidate))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.error("Failed to generate a unique username for firstName={}, lastName={}", firstName, lastName);
                    return new IllegalStateException("No unique username could be generated.");
                });
    }

    private boolean isUsernameUnique(String username) {
        boolean isUnique = trainerDAO.findByUsername(username).isEmpty()
                && traineeDAO.findByUsername(username).isEmpty();

        if (!isUnique) {
            LOGGER.debug("Username '{}' is already taken.", username);
        }

        return isUnique;
    }
}
