package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
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

    public Trainee updateTrainee(String authUsername, String authPassword,Trainee traineeUpdate) {
        return traineeService.updateProfile(authUsername, authPassword, traineeUpdate);
    }

    public void deleteTrainee(String authUsername, String authPassword, String username) {
        traineeService.delete(authUsername, authPassword, username);
    }

    public Trainee getTraineeByUsername(String authUsername, String authPassword, String username) {
        return traineeService.findByUsername(authUsername, authPassword, username);
    }

    public Trainee getTraineeById(Long id) {
        return traineeService.findById(id);
    }

    public void toggleTraineeStatus(String authUsername, String authPassword, String username) {
        traineeService.toggleActiveStatus(authUsername, authPassword, username);
    }

    public List<Training> getTraineeTrainings(String authUsername,
                                              String authPassword,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String trainerUsername,
                                              String trainingType) {
        return traineeService.getTraineeTrainings(authUsername, authPassword, username,
                fromDate, toDate, trainerUsername, trainingType);
    }

    public String changeTraineePassword(String authUsername, String authPassword, String username) {
        return traineeService.changePassword(authUsername, authPassword, username);
    }

    public Trainer registerTrainer(Trainer newTrainer) {
        return trainerService.createProfile(newTrainer);
    }

    public Trainer updateTrainer(String authUsername, String authPassword, Trainer trainerUpdate) {
        return trainerService.updateProfile(authUsername, authPassword, trainerUpdate);
    }

    public Trainer getTrainerByUsername(String authUsername, String authPassword, String username) {
        return trainerService.findByUsername(authUsername, authPassword, username);
    }

    public Trainer getTrainerById(Long id) {
        return trainerService.findById(id);
    }

    public void toggleTrainerStatus(String authUsername, String authPassword, String username) {
        trainerService.toggleActiveStatus(authUsername, authPassword, username);
    }

    public List<Training> getTrainerTrainings(String authUsername,
                                              String authPassword,
                                              String username,
                                              LocalDate fromDate,
                                              LocalDate toDate,
                                              String traineeUsername) {
        return trainerService.getTrainerTrainings(authUsername, authPassword, username,
                fromDate, toDate, traineeUsername);
    }

    public String changeTrainerPassword(String authUsername, String authPassword, String username) {
        return trainerService.changePassword(authUsername, authPassword, username);
    }

    public Training addTraining(String authUsername, String authPassword, Training training) {
        return trainingService.addTraining(authUsername, authPassword, training);
    }
}
