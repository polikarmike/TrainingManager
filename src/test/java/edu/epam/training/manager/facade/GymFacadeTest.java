package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.service.CreateReadService;
import edu.epam.training.manager.service.CreateReadUpdateService;
import edu.epam.training.manager.service.CrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GymFacadeTest {

    private CrudService<Trainee, UUID> traineeService;
    private CreateReadUpdateService<Trainer, UUID> trainerService;
    private CreateReadService<Training, UUID> trainingService;
    private GymFacade gymFacade;

    @SuppressWarnings("unchecked")
    @BeforeEach
    void setUp() {
        traineeService = (CrudService<Trainee, UUID>) mock(CrudService.class);
        trainerService = (CreateReadUpdateService<Trainer, UUID>) mock(CreateReadUpdateService.class);
        trainingService = (CreateReadService<Training, UUID>) mock(CreateReadService.class);
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

        when(traineeService.create(newTrainee)).thenReturn(newTrainee);

        Trainee result = gymFacade.registerTrainee(newTrainee);

        assertEquals(newTrainee, result);
        verify(traineeService).create(newTrainee);
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

        when(trainerService.update(trainer)).thenReturn(trainer);

        Trainer updated = gymFacade.updateTrainer(trainer);

        assertEquals(trainer, updated);
        verify(trainerService).update(trainer);
    }

    @Test
    void deleteTrainee_shouldCallService() {
        UUID id = UUID.randomUUID();

        gymFacade.deleteTrainee(id);

        verify(traineeService).delete(id);
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


        when(trainingService.findById(trainingId)).thenReturn(training);

        Training result = gymFacade.getTraining(trainingId);

        assertEquals(training, result);
        verify(trainingService).findById(trainingId);
    }

    @Test
    void updateTrainee_shouldDelegateToTraineeService() {
        Trainee traineeUpdate = Trainee.builder()
                .id(UUID.randomUUID())
                .firstName("Jane")
                .lastName("Doe")
                .username("janeDoe")
                .password("password")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1992, 5, 12))
                .address("456 Main St")
                .build();
        when(traineeService.update(traineeUpdate)).thenReturn(traineeUpdate);

        Trainee result = gymFacade.updateTrainee(traineeUpdate);

        assertEquals(traineeUpdate, result);
        verify(traineeService).update(traineeUpdate);
    }

    @Test
    void getTrainee_shouldReturnTraineeFromService() {
        UUID traineeId = UUID.randomUUID();
        Trainee trainee = Trainee.builder()
                .id(traineeId)
                .firstName("Jane")
                .lastName("Doe")
                .username("janeDoe")
                .password("password")
                .isActive(true)
                .dateOfBirth(LocalDate.of(1992, 5, 12))
                .address("456 Main St")
                .build();
        when(traineeService.findById(traineeId)).thenReturn(trainee);

        Trainee result = gymFacade.getTrainee(traineeId);

        assertEquals(trainee, result);
        verify(traineeService).findById(traineeId);
    }

    @Test
    void registerTrainer_shouldDelegateToTrainerService() {
        Trainer trainer = Trainer.builder()
                .id(UUID.randomUUID())
                .firstName("Anna")
                .lastName("Smith")
                .username("asmith")
                .password("pass123")
                .isActive(true)
                .specialization(TrainingType.YOGA)
                .build();
        when(trainerService.create(trainer)).thenReturn(trainer);

        Trainer result = gymFacade.registerTrainer(trainer);

        assertEquals(trainer, result);
        verify(trainerService).create(trainer);
    }

    @Test
    void getTrainer_shouldReturnTrainerFromService() {
        // Arrange
        UUID trainerId = UUID.randomUUID();
        Trainer trainer = Trainer.builder()
                .id(trainerId)
                .firstName("Anna")
                .lastName("Smith")
                .username("asmith")
                .password("pass123")
                .isActive(true)
                .specialization(TrainingType.YOGA)
                .build();
        when(trainerService.findById(trainerId)).thenReturn(trainer);

        Trainer result = gymFacade.getTrainer(trainerId);

        assertEquals(trainer, result);
        verify(trainerService).findById(trainerId);
    }

    @Test
    void registerTraining_shouldDelegateToTrainingService() {
        UUID trainingId = UUID.randomUUID();
        UUID trainerId = UUID.randomUUID();
        UUID traineeId = UUID.randomUUID();
        Training training = Training.builder()
                .id(trainingId)
                .trainingName("Evening Pilates")
                .trainingType(TrainingType.PILATES)
                .trainerId(trainerId)
                .traineeId(traineeId)
                .trainingDate(LocalDate.now())
                .trainingDuration(Duration.ofMinutes(60))
                .build();
        when(trainingService.create(training)).thenReturn(training);

        Training result = gymFacade.registerTraining(training);

        assertEquals(training, result);
        verify(trainingService).create(training);
    }
}
