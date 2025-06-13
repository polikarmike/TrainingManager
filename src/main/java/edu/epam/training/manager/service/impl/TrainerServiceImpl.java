package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.service.AuthenticationService;
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

    private static final String LOG_QUERY_START    =
            SERVICE_NAME + " - Fetching trainings for trainer {} with [from={}, to={}, trainee={}]";
    private static final String LOG_QUERY_RESULTS  =
            SERVICE_NAME + " - Retrieved {} trainings for trainer {}";

    private static final String LOG_UNASSIGNED_SEARCH_START   =
            SERVICE_NAME + " - Initiating search for unassigned trainers (without trainees).";
    private static final String LOG_UNASSIGNED_SEARCH_SUCCESS =
            SERVICE_NAME + " - Search completed. Found {} unassigned trainers.";

    private final AuthenticationService authenticationService;
    private final TrainerDao trainerDao;
    private final  UserService userService;
    private final PasswordGenerator passwordGenerator;

    public TrainerServiceImpl(AuthenticationService authenticationService,
                              TrainerDao trainerDao,
                              UserService userService,
                              PasswordGenerator passwordGenerator) {

        this.authenticationService = authenticationService;
        this.trainerDao = trainerDao;
        this.userService = userService;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public TrainerDao getDao() {
        return trainerDao;
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
    public Trainer buildProfile(User user, Trainer profile) {
        return Trainer.builder()
                .user(user)
                .specialization(profile.getSpecialization())
                .build();
    }

    @Override
    public void updateProfileSpecificFields(Trainer existing, Trainer item) {
        Optional.ofNullable(item.getSpecialization()).ifPresent(existing::setSpecialization);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Trainer> findUnassignedTrainers(Credentials authCredentials) {
        LOGGER.debug(LOG_UNASSIGNED_SEARCH_START);

        authenticationService.authenticateCredentials(authCredentials);

        List<Trainer> trainers = trainerDao.findUnassignedTrainers();

        LOGGER.debug(LOG_UNASSIGNED_SEARCH_SUCCESS, trainers.size());
        return trainers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Training> getTrainerTrainings(Credentials authCredentials,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String traineeUsername) {
        LOGGER.debug(LOG_QUERY_START,
                username, fromDate, toDate, traineeUsername);

        authenticationService.authenticateCredentials(authCredentials);

        List<Training> trainings = trainerDao.getTrainerTrainings(username, fromDate, toDate, traineeUsername);

        LOGGER.debug(LOG_QUERY_RESULTS, trainings.size(), username);
        return trainings;
    }
}
