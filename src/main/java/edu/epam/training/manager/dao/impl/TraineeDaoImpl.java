package edu.epam.training.manager.dao.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.dao.base.AbstractCrudDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.UserStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.UUID;

public class TraineeDaoImpl extends AbstractCrudDao<Trainee, UserStorage<Trainee, UUID>, UUID>
    implements TraineeDao<Trainee, UUID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);


    @Override
    public Optional<Trainee> findByUsername(String username) {
        LOGGER.debug("DAO TRAINEE READ: Searching for trainee with username '{}'", username);

        Trainee result;

        try {
            result = getStorage().findByUsername(username);
        } catch (Exception e) {
            LOGGER.error("DAO TRAINEE READ: Error searching for trainee with username '{}': {}", username, e.getMessage(), e);
            throw e;
        }

        return Optional.ofNullable(result);
    }
}
