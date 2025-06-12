package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.service.AuthService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.UserService;
import edu.epam.training.manager.utils.generation.PasswordGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainerServiceImpl.class);

    private static final String SERVICE_NAME = "TrainerServiceImpl";

    private static final String LOG_CREATE_TRAINER_START = SERVICE_NAME + " - Starting creation for trainer: {} {}";
    private static final String LOG_CREATE_SUCCESS = SERVICE_NAME + " - Created trainer: {}";

    private static final String LOG_UPDATE_START   = SERVICE_NAME + " - Starting update for trainer ID: {}";
    private static final String LOG_UPDATE_SUCCESS = SERVICE_NAME + " - Updated trainer ID: {}";

    private static final String LOG_QUERY_START    = SERVICE_NAME + " - Fetching trainings for trainer {} with [from={}, to={}, trainee={}]";
    private static final String LOG_QUERY_RESULTS  = SERVICE_NAME + " - Retrieved {} trainings for trainer {}";

    private static final String ERR_NOT_FOUND_BY_ID    = SERVICE_NAME + ": No trainer found with ID '%s'";

    private final AuthService authService;
    private final TrainerDao trainerDao;
    private final  UserService userService;
    private final PasswordGenerator passwordGenerator;

    public TrainerServiceImpl(AuthService authService,
                              TrainerDao trainerDao,
                              UserService userService,
                              PasswordGenerator passwordGenerator) {

        this.authService = authService;
        this.trainerDao = trainerDao;
        this.userService = userService;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public TrainerDao getDao() {
        return trainerDao;
    }

    @Override
    public AuthService getAuthService() {
        return authService;
    }

    @Override
    public PasswordGenerator getPasswordGenerator() {
        return passwordGenerator;
    }

    @Override
    @Transactional
    public Trainer createProfile(Trainer trainer) {
        LOGGER.debug(LOG_CREATE_TRAINER_START,
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName());

        String username = userService.generateUniqueUsername(
                trainer.getUser().getFirstName(),
                trainer.getUser().getLastName());

        String password = passwordGenerator.generate();

        User newUser = User.builder()
                .firstName(trainer.getUser().getFirstName())
                .lastName(trainer.getUser().getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();

        Trainer newTrainer = Trainer.builder()
                .user(newUser)
                .specialization(trainer.getSpecialization())
                .build();

        Trainer created = trainerDao.create(newTrainer);

        LOGGER.debug(LOG_CREATE_SUCCESS, created);
        return created;
    }

    @Override
    @Transactional
    public Trainer updateProfile(String authUsername, String authPassword, Trainer trainer) {
        LOGGER.debug(LOG_UPDATE_START, trainer.getId());
        authService.authenticateCredentials(authUsername, authPassword);

        Trainer existing = trainerDao.findById(trainer.getId())
                .orElseThrow(() -> {
                    String msg = String.format(ERR_NOT_FOUND_BY_ID, trainer.getId());
                    LOGGER.error(msg);
                    return new EntityNotFoundException(msg);
                });

        User existingUser = existing.getUser();

        Optional.ofNullable(trainer.getUser().getFirstName())
                .ifPresent(existingUser::setFirstName);

        Optional.ofNullable(trainer.getUser().getLastName())
                .ifPresent(existingUser::setLastName);

        existingUser.setActive(trainer.getUser().isActive());

        Optional.ofNullable(trainer.getSpecialization())
                .ifPresent(existing::setSpecialization);

        Trainer updated = trainerDao.update(existing);

        LOGGER.debug(LOG_UPDATE_SUCCESS, updated.getId());
        return updated;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> findUnassignedTrainers(String authUsername, String authPassword) {
        LOGGER.debug("Initiating search for unassigned trainers (without trainees).");

        authService.authenticateCredentials(authUsername, authPassword);

        List<Trainer> trainers = trainerDao.findUnassignedTrainers();

        LOGGER.debug("Search completed. Found {} unassigned trainers.", trainers.size());
        return trainers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainings(String authUsername,
                                              String authPassword,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String traineeUsername) {
        LOGGER.debug(LOG_QUERY_START,
                username, fromDate, toDate, traineeUsername);

        authService.authenticateCredentials(authUsername, authPassword);

        List<Training> trainings = trainerDao.getTrainerTrainings(username, fromDate, toDate, traineeUsername);

        LOGGER.debug(LOG_QUERY_RESULTS, trainings.size(), username);
        return trainings;
    }
}
