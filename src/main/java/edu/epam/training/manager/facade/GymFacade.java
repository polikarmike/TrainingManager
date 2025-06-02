package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.TrainingService;

import java.util.UUID;

public class GymFacade {
    private final TraineeService traineeService;
    private final TrainerService trainerService;
    private final TrainingService trainingService;

    public GymFacade(
            TraineeService traineeService,
            TrainerService trainerService,
            TrainingService trainingService) {

        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee registerTrainee(Trainee newTrainee) {
        return traineeService.createProfile(newTrainee);
    }

    public Trainee updateTrainee(Trainee traineeUpdate) {
        return traineeService.updateProfile(traineeUpdate);
    }

    public void deleteTrainee(UUID id) {
        traineeService.deleteProfile(id);
    }

    public Trainee getTrainee(UUID id) {
        return traineeService.getProfile(id);
    }

    public Trainer registerTrainer(Trainer trainer) {
        return trainerService.createProfile(trainer);
    }

    public Trainer updateTrainer(Trainer trainerUpdate) {
        return trainerService.updateProfile(trainerUpdate);
    }

    public Trainer getTrainer(UUID id) {
        return trainerService.getProfile(id);
    }

    public Training registerTraining(Training training) {
        return trainingService.createTraining(training);
    }

    public Training getTraining(UUID id) {
        return trainingService.getTraining(id);
    }
}


