package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.service.AuthService;
import edu.epam.training.manager.service.TraineeService;
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
public class TraineeServiceImpl implements TraineeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeServiceImpl.class);

    private final AuthService authService;
    private final TraineeDao traineeDao;
    private final UserService userService;
    private final PasswordGenerator passwordGenerator;

    private static final String SERVICE_NAME = "TraineeServiceImpl";

    private static final String LOG_CREATE_START   = SERVICE_NAME + " - Starting creation for trainee: {} {}";
    private static final String LOG_CREATE_SUCCESS = SERVICE_NAME + " - Created trainee: {}";

    private static final String LOG_UPDATE_START   = SERVICE_NAME + " - Starting update for trainee ID: {}";
    private static final String LOG_UPDATE_SUCCESS = SERVICE_NAME + " - Updated trainee ID: {}";

    private static final String LOG_DELETE_START   = SERVICE_NAME + " - Deleting trainee by username: {}";
    private static final String LOG_DELETE_SUCCESS = SERVICE_NAME + " - Deleted trainee: {}";

    private static final String LOG_QUERY_START    = SERVICE_NAME + " - Fetching trainings for trainee {} with [from={}, to={}, trainer={}, type={}]";
    private static final String LOG_QUERY_RESULTS  = SERVICE_NAME + " - Retrieved {} trainings for trainee {}";

    private static final String ERR_NOT_FOUND_BY_ID    = SERVICE_NAME + ": No trainee found with ID '%s'";

    public TraineeServiceImpl(AuthService authService,
                              TraineeDao traineeDao,
                              UserService userService,
                              PasswordGenerator passwordGenerator) {
        this.authService = authService;
        this.traineeDao = traineeDao;
        this.userService = userService;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public TraineeDao getDao() {
        return traineeDao;
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
    public Trainee createProfile(Trainee trainee) {
        LOGGER.debug(LOG_CREATE_START,
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName());

        String username = userService.generateUniqueUsername(
                trainee.getUser().getFirstName(),
                trainee.getUser().getLastName());

        String password = passwordGenerator.generate();

        User newUser = User.builder()
                .firstName(trainee.getUser().getFirstName())
                .lastName(trainee.getUser().getLastName())
                .username(username)
                .password(password)
                .isActive(true)
                .build();

        Trainee newTrainee = Trainee.builder()
                .user(newUser)
                .dateOfBirth(trainee.getDateOfBirth())
                .address(trainee.getAddress())
                .build();

        Trainee created = traineeDao.create(newTrainee);

        LOGGER.debug(LOG_CREATE_SUCCESS, created);
        return created;
    }

    @Override
    @Transactional
    public Trainee updateProfile(String authUsername, String authPassword, Trainee trainee) {
        LOGGER.debug(LOG_UPDATE_START, trainee.getId());

        authService.authenticateCredentials(authUsername, authPassword);

        Trainee existing = traineeDao.findById(trainee.getId())
                .orElseThrow(() -> {
                    String msg = String.format(ERR_NOT_FOUND_BY_ID, trainee.getId());
                    LOGGER.error(msg);
                    return new EntityNotFoundException(msg);
                });

        User existingUser = existing.getUser();

        Optional.ofNullable(trainee.getUser().getFirstName())
                .ifPresent(existingUser::setFirstName);

        Optional.ofNullable(trainee.getUser().getLastName())
                .ifPresent(existingUser::setLastName);

        Optional.ofNullable(trainee.getDateOfBirth())
                .ifPresent(existing::setDateOfBirth);

        Optional.ofNullable(trainee.getAddress())
                .ifPresent(existing::setAddress);

        traineeDao.update(existing);

        Trainee updated = traineeDao.update(existing);

        LOGGER.debug(LOG_UPDATE_SUCCESS, existing.getId());
        return updated;
    }

    @Override
    @Transactional
    public void delete(String authUsername, String authPassword, String username) {
        LOGGER.debug(LOG_DELETE_START, username);

        authService.authenticateCredentials(authUsername, authPassword);

        Long id = findByUsername(authUsername, authPassword, username).getId();

        traineeDao.delete(id);
        LOGGER.debug(LOG_DELETE_SUCCESS, username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainings(String authUsername,
                                              String authPassword,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String trainerUsername,
                                              String trainingType) {

        LOGGER.debug(LOG_QUERY_START, username, fromDate, toDate, trainerUsername, trainingType);

        authService.authenticateCredentials(authUsername, authPassword);

        List<Training> trainings = traineeDao.getTraineeTrainings(username, fromDate, toDate, trainerUsername, trainingType);

        LOGGER.debug(LOG_QUERY_RESULTS, trainings.size(), username);
        return trainings;
    }
}
