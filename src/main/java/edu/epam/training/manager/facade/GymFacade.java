package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.service.CreateReadService;
import edu.epam.training.manager.service.CreateReadUpdateService;
import edu.epam.training.manager.service.CrudService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GymFacade {
    private final CrudService<Trainee, UUID> traineeService;
    private final CreateReadUpdateService<Trainer, UUID> trainerService;
    private final CreateReadService<Training, UUID> trainingService;

    public GymFacade(
            CrudService<Trainee, UUID> traineeService,
            CreateReadUpdateService<Trainer, UUID> trainerService,
            CreateReadService<Training, UUID> trainingService) {

        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.trainingService = trainingService;
    }

    public Trainee registerTrainee(Trainee newTrainee) {
        return traineeService.create(newTrainee);
    }

    public Trainee updateTrainee(Trainee traineeUpdate) {
        return traineeService.update(traineeUpdate);
    }

    public void deleteTrainee(UUID id) {
        traineeService.delete(id);
    }

    public Trainee getTrainee(UUID id) {
        return traineeService.findById(id);
    }

    public Trainer registerTrainer(Trainer trainer) {
        return trainerService.create(trainer);
    }

    public Trainer updateTrainer(Trainer trainerUpdate) {
        return trainerService.update(trainerUpdate);
    }

    public Trainer getTrainer(UUID id) {
        return trainerService.findById(id);
    }

    public Training registerTraining(Training training) {
        return trainingService.create(training);
    }

    public Training getTraining(UUID id) {
        return trainingService.findById(id);
    }
}
