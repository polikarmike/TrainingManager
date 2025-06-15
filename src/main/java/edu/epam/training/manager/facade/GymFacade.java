package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.TrainingService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee registerTrainee(Trainee newTrainee) {
        return traineeService.createProfile(newTrainee);
    }

    public Trainee updateTrainee(Credentials authCredentials,Trainee traineeUpdate) {
        return traineeService.updateProfile(authCredentials, traineeUpdate);
    }

    public void deleteTrainee(Credentials authCredentials, String username) {
        traineeService.delete(authCredentials, username);
    }

    public Trainee getTraineeByUsername(Credentials authCredentials, String username) {
        return traineeService.findByUsername(authCredentials, username);
    }

    public Trainee getTraineeById(Credentials authCredentials, Long id) {
        return traineeService.findById(authCredentials, id);
    }

    public void toggleTraineeStatus(Credentials authCredentials, String username) {
        traineeService.toggleActiveStatus(authCredentials, username);
    }

    public List<Training> getTraineeTrainings(Credentials authCredentials,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String trainerUsername,
                                              String trainingType) {
        return traineeService.getTraineeTrainings(authCredentials, username,
                fromDate, toDate, trainerUsername, trainingType);
    }

    public String changeTraineePassword(Credentials authCredentials, String username) {
        return traineeService.changePassword(authCredentials, username);
    }

    public Trainer registerTrainer(Trainer newTrainer) {
        return trainerService.createProfile(newTrainer);
    }

    public Trainer updateTrainer(Credentials authCredentials, Trainer trainerUpdate) {
        return trainerService.updateProfile(authCredentials, trainerUpdate);
    }

    public Trainer getTrainerByUsername(Credentials authCredentials, String username) {
        return trainerService.findByUsername(authCredentials, username);
    }

    public Trainer getTrainerById(Credentials authCredentials, Long id) {
        return trainerService.findById(authCredentials, id);
    }

    public void toggleTrainerStatus(Credentials authCredentials, String username) {
        trainerService.toggleActiveStatus(authCredentials, username);
    }

    public List<Training> getTrainerTrainings(Credentials authCredentials,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String traineeUsername) {
        return trainerService.getTrainerTrainings(authCredentials, username,
                fromDate, toDate, traineeUsername);
    }

    public String changeTrainerPassword(Credentials authCredentials, String username) {
        return trainerService.changePassword(authCredentials, username);
    }

    public List<Trainer> findUnassignedTrainers(Credentials authCredentials, String traineeUsername) {
        return trainerService.findUnassignedTrainers(authCredentials, traineeUsername);
    }

    public Training addTraining(Credentials authCredentials, Training training) {
        return trainingService.addTraining(authCredentials, training);
    }
}
