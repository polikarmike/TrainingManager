package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.dao.BaseDAO;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.TrainingType;
import edu.epam.trainingmanager.service.impl.TrainingServiceImpl;
import edu.epam.trainingmanager.service.impl.TraineeServiceImpl;
import edu.epam.trainingmanager.service.impl.TrainerServiceImpl;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    private BaseDAO<Training> trainingDAO;
    @Mock
    private IdGenerator idGenerator;
    @Mock
    private TraineeServiceImpl traineeServiceImpl;
    @Mock
    private TrainerServiceImpl trainerServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTraining_ShouldCreateAndReturnTraining() {
        Training training = new Training();
        training.setTraineeId("trainee123");
        training.setTrainerId("trainer456");
        training.setTrainingType(new TrainingType("Fitness"));
        training.setTrainingName("Morning Workout");

        Trainee trainee = new Trainee();
        trainee.setId("trainee123");

        Trainer trainer = new Trainer();
        trainer.setId("trainer456");
        trainer.setSpecialization(new TrainingType("Fitness"));

        when(traineeServiceImpl.getProfile("trainee123")).thenReturn(trainee);
        when(trainerServiceImpl.getProfile("trainer456")).thenReturn(trainer);
        when(idGenerator.generate()).thenReturn("training789");

        Training createdTraining = trainingService.createTraining(training);

        assertNotNull(createdTraining);
        assertEquals("training789", createdTraining.getId());
        assertEquals("Morning Workout", createdTraining.getTrainingName());
        assertEquals("Fitness", createdTraining.getTrainingType().getName());
        assertEquals("trainee123", createdTraining.getTraineeId());
        assertEquals("trainer456", createdTraining.getTrainerId());

        verify(trainingDAO, times(1)).create(createdTraining);
    }

    @Test
    void createTraining_ShouldThrowExceptionIfTraineeNotFound() {
        when(traineeServiceImpl.getProfile("trainee123")).thenReturn(null);

        Training training = new Training();
        training.setTraineeId("trainee123");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingService.createTraining(training));
        assertTrue(exception.getMessage().contains("Trainee with ID trainee123 does not exist."));
    }

    @Test
    void createTraining_ShouldThrowExceptionIfTrainerNotFound() {
        Trainee trainee = new Trainee();
        trainee.setId("trainee123");

        when(traineeServiceImpl.getProfile("trainee123")).thenReturn(trainee);
        when(trainerServiceImpl.getProfile("trainer456")).thenReturn(null);

        Training training = new Training();
        training.setTraineeId("trainee123");
        training.setTrainerId("trainer456");

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingService.createTraining(training));
        assertTrue(exception.getMessage().contains("Trainer with ID trainer456 does not exist."));
    }

    @Test
    void getTraining_ShouldReturnTrainingIfExists() {
        Training training = new Training();
        training.setId("training789");

        when(trainingDAO.findById("training789")).thenReturn(Optional.of(training));

        Training result = trainingService.getTraining("training789");

        assertNotNull(result);
        assertEquals("training789", result.getId());
    }

    @Test
    void getTraining_ShouldThrowExceptionIfNotFound() {
        when(trainingDAO.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainingService.getTraining("999"));
        assertTrue(exception.getMessage().contains("Training with ID 999 not found."));
    }

    @Test
    void getAllTrainings_ShouldReturnListOfTrainings() {
        Training training1 = new Training();
        Training training2 = new Training();

        when(trainingDAO.findAll()).thenReturn(List.of(training1, training2));

        List<Training> result = trainingService.getAllTrainings();

        assertEquals(2, result.size());
    }

    @Test
    void findTrainingsByTrainer_ShouldReturnCorrectTrainings() {
        Training training1 = new Training();
        training1.setTrainerId("trainer123");

        Training training2 = new Training();
        training2.setTrainerId("trainer456");

        when(trainingDAO.findAll()).thenReturn(List.of(training1, training2));

        List<Training> result = trainingService.findTrainingsByTrainer("trainer123");

        assertEquals(1, result.size());
        assertEquals("trainer123", result.getFirst().getTrainerId());
    }

    @Test
    void findTrainingsByTrainee_ShouldReturnCorrectTrainings() {
        Training training1 = new Training();
        training1.setTraineeId("trainee123");

        Training training2 = new Training();
        training2.setTraineeId("trainee456");

        when(trainingDAO.findAll()).thenReturn(List.of(training1, training2));

        List<Training> result = trainingService.findTrainingsByTrainee("trainee123");

        assertEquals(1, result.size());
        assertEquals("trainee123", result.getFirst().getTraineeId());
    }
}

