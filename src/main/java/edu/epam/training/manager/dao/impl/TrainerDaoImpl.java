package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.dao.base.AbstractCreateReadUpdateDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class TrainerDaoImpl extends AbstractCreateReadUpdateDao<Trainer, UserStorage<Trainer, UUID>, UUID>
        implements TrainerDao<Trainer, UUID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerDaoImpl.class);

    @Override
    public Optional<Trainer> findByUsername(String username) {
        LOGGER.debug("DAO TRAINER READ: Searching for trainer with username '{}'", username);

        Trainer result;

        try {
            result = getStorage().findByUsername(username);
        } catch (Exception e) {
            LOGGER.error("DAO TRAINER READ: Error searching for trainer with username '{}': {}", username, e.getMessage(), e);
            throw e;
        }

        return Optional.ofNullable(result);
    }
}
