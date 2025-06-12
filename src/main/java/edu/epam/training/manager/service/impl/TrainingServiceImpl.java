package edu.epam.training.manager.service.impl;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.exception.InvalidStateException;
import edu.epam.training.manager.service.AuthService;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.TrainingService;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Setter
public class TrainingServiceImpl implements TrainingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingServiceImpl.class);

    private static final String SERVICE_NAME = "TrainingServiceImpl";

    private static final String LOG_ADD_START    = SERVICE_NAME + " - Starting training creation: {}";
    private static final String LOG_ADD_SUCCESS  = SERVICE_NAME + " - Created training: {}";

    private static final String ERR_INVALID_SPECIALIZATION  =
            SERVICE_NAME + ": Trainer specialization '%s' does not match required '%s'";

    private final TrainingDao trainingDao;
    private final AuthService authService;
    private final TraineeService traineeService;
    private final TrainerService trainerService;

    public TrainingServiceImpl(TrainingDao trainingDao, AuthService authService, TraineeService traineeService, TrainerService trainerService) {
        this.trainingDao = trainingDao;
        this.authService = authService;
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }


    @Override
    @Transactional
    public Training addTraining(String authUsername, String authPassword, Training training) {
        LOGGER.debug(LOG_ADD_START, training.getTrainingName());
        authService.authenticateCredentials(authUsername, authPassword);

        Long traineeId = training.getTrainee().getId();
        Trainee trainee =traineeService.findById(traineeId);

        Long trainerId = training.getTrainer().getId();
        Trainer trainer = trainerService.findById(trainerId);

        validateTrainerSpecialization(trainer.getSpecialization(), training.getTrainingType());

        Training newTraining = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingDuration(training.getTrainingDuration())
                .trainingDate(training.getTrainingDate())
                .trainingType(training.getTrainingType())
                .build();

        Training created = trainingDao.create(newTraining);

        LOGGER.debug(LOG_ADD_SUCCESS, created);
        return created;
    }

    private void validateTrainerSpecialization(TrainingType actual, TrainingType required) {
        if (!actual.equals(required)) {
            String msg = String.format(ERR_INVALID_SPECIALIZATION, actual, required);
            LOGGER.error(msg);
            throw new InvalidStateException(msg);
        }
    }
}
