package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.service.AuthenticationService;
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

    private final AuthenticationService authenticationService;
    private final TraineeDao traineeDao;
    private final UserService userService;
    private final PasswordGenerator passwordGenerator;

    private static final String SERVICE_NAME = "TraineeServiceImpl";

    private static final String LOG_DELETE_START   = SERVICE_NAME + " - Deleting trainee by username: {}";
    private static final String LOG_DELETE_SUCCESS = SERVICE_NAME + " - Deleted trainee: {}";

    private static final String LOG_QUERY_START    = SERVICE_NAME + " - Fetching trainings for trainee {} with [from={}, to={}, trainer={}, type={}]";
    private static final String LOG_QUERY_RESULTS  = SERVICE_NAME + " - Retrieved {} trainings for trainee {}";

    public TraineeServiceImpl(AuthenticationService authenticationService,
                              TraineeDao traineeDao,
                              UserService userService,
                              PasswordGenerator passwordGenerator) {
        this.authenticationService = authenticationService;
        this.traineeDao = traineeDao;
        this.userService = userService;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public TraineeDao getDao() {
        return traineeDao;
    }

    @Override
    public AuthenticationService getAuthService() {
        return authenticationService;
    }

    @Override
    public PasswordGenerator getPasswordGenerator() {
        return passwordGenerator;
    }

    @Override
    public UserService getUserService() {
        return userService;
    }

    @Override
    public Trainee buildProfile(User user, Trainee profile) {
        return Trainee.builder()
                .user(user)
                .dateOfBirth(profile.getDateOfBirth())
                .address(profile.getAddress())
                .build();
    }

    @Override
    public void updateProfileSpecificFields(Trainee existing, Trainee item) {
        Optional.ofNullable(item.getDateOfBirth()).ifPresent(existing::setDateOfBirth);
        Optional.ofNullable(item.getAddress()).ifPresent(existing::setAddress);
    }

    @Override
    @Transactional
    public void delete(Credentials authCredentials, String username) {
        LOGGER.debug(LOG_DELETE_START, username);

        authenticationService.authenticateCredentials(authCredentials);

        Long id = findByUsername(authCredentials, username).getId();

        traineeDao.delete(id);
        LOGGER.debug(LOG_DELETE_SUCCESS, username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTraineeTrainings(Credentials authCredentials,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String trainerUsername,
                                              String trainingType) {

        LOGGER.debug(LOG_QUERY_START, username, fromDate, toDate, trainerUsername, trainingType);

        authenticationService.authenticateCredentials(authCredentials);

        List<Training> trainings = traineeDao.getTraineeTrainings(username, fromDate, toDate, trainerUsername, trainingType);

        LOGGER.debug(LOG_QUERY_RESULTS, trainings.size(), username);
        return trainings;
    }
}
