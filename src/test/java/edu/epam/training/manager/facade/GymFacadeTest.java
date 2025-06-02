package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GymFacadeTest {

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TrainingService trainingService;
    private GymFacade gymFacade;

    @BeforeEach
    void setUp() {
        traineeService = mock(TraineeService.class);
        trainerService = mock(TrainerService.class);
        trainingService = mock(TrainingService.class);
        gymFacade = new GymFacade(traineeService, trainerService, trainingService);
    }

    @Test
    void registerTrainee_shouldDelegateToService() {
        Trainee newTrainee = Trainee.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .username("john123")
                .password("secret")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .build();

        when(traineeService.createProfile(newTrainee)).thenReturn(newTrainee);

        Trainee result = gymFacade.registerTrainee(newTrainee);

        assertEquals(newTrainee, result);
        verify(traineeService).createProfile(newTrainee);
    }

    @Test
    void updateTrainer_shouldReturnUpdatedTrainer() {
        Trainer trainer = Trainer.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Smith")
                .username("asmith")
                .password("pass123")
                .isActive(true)
                .specialization(TrainingType.YOGA)
                .build();

        when(trainerService.updateProfile(trainer)).thenReturn(trainer);

        Trainer updated = gymFacade.updateTrainer(trainer);

        assertEquals(trainer, updated);
        verify(trainerService).updateProfile(trainer);
    }

    @Test
    void deleteTrainee_shouldCallService() {
        UUID id = UUID.randomUUID();

        gymFacade.deleteTrainee(id);

        verify(traineeService).deleteProfile(id);
    }

    @Test
    void getTraining_shouldReturnTraining() {
        UUID trainingId = UUID.randomUUID();

        Training training = Training.builder()
                .id(trainingId)
                .trainingName("Morning Cardio")
                .trainingType(TrainingType.CARDIO)
                .trainerId(UUID.randomUUID())
                .traineeId(UUID.randomUUID())
                .trainingDate(LocalDate.now())
                .trainingDuration(Duration.ofMinutes(45))
                .build();

        when(trainingService.getTraining(trainingId)).thenReturn(training);

        Training result = gymFacade.getTraining(trainingId);

        assertEquals(training, result);
        verify(trainingService).getTraining(trainingId);
    }

}


