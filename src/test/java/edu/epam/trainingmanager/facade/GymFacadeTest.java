package edu.epam.trainingmanager.facade;

import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.domain.TrainingType;
import edu.epam.trainingmanager.dto.trainee.TraineeCreateDto;
import edu.epam.trainingmanager.dto.trainee.TraineeDto;
import edu.epam.trainingmanager.dto.trainee.TraineeUpdateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerCreateDto;
import edu.epam.trainingmanager.dto.trainer.TrainerDto;
import edu.epam.trainingmanager.dto.trainer.TrainerUpdateDto;
import edu.epam.trainingmanager.dto.training.TrainingCreateDto;
import edu.epam.trainingmanager.dto.training.TrainingDto;
import edu.epam.trainingmanager.service.TraineeService;
import edu.epam.trainingmanager.service.TrainerService;
import edu.epam.trainingmanager.service.TrainingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GymFacadeTest {

    @InjectMocks
    private GymFacade gymFacade;

    @Mock
    private TraineeService traineeService;
    @Mock
    private TrainerService trainerService;
    @Mock
    private TrainingService trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerTrainee_ShouldCreateAndReturnTraineeDto() {
        TraineeCreateDto dto = new TraineeCreateDto("John", "Doe", LocalDate.of(2000, 1, 1), "New York");

        Trainee trainee = Trainee.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("New York")
                .isActive(true)
                .username("john.doe")
                .password("password")
                .build();

        when(traineeService.createProfile(any())).thenReturn(trainee);

        TraineeDto result = gymFacade.registerTrainee(dto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
    }

    @Test
    void updateTrainee_ShouldUpdateAndReturnTraineeDto() {
        TraineeUpdateDto dto = new TraineeUpdateDto("1", "John", null, null, null, null);

        Trainee existing = Trainee.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("New York")
                .isActive(true)
                .username("john.doe")
                .password("password")
                .build();

        Trainee updated = Trainee.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("New York")
                .isActive(true)
                .username("john.doe")
                .password("password")
                .build();

        when(traineeService.getProfile("1")).thenReturn(existing);
        when(traineeService.updateProfile(any())).thenReturn(updated);

        TraineeDto result = gymFacade.updateTrainee(dto);

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void registerTraining_ShouldCreateAndReturnTrainingDto() {
        TrainingCreateDto dto = new TrainingCreateDto("Morning Yoga", new TrainingType("Yoga"), "2", "1", LocalDate.of(2025, 6, 10), Duration.ofMinutes(60));

        Training training = Training.builder()
                .id("10")
                .trainingName("Morning Yoga")
                .trainingType(new TrainingType("Yoga"))
                .trainerId("2")
                .traineeId("1")
                .trainingDate(LocalDate.of(2025, 6, 10))
                .trainingDuration(Duration.ofMinutes(60))
                .build();

        when(trainingService.createTraining(any())).thenReturn(training);

        TrainingDto result = gymFacade.registerTraining(dto);

        assertNotNull(result);
        assertEquals("Morning Yoga", result.getTrainingName());
        assertEquals("Yoga", result.getTrainingType().getName());
    }

    @Test
    void getTrainee_ShouldReturnTraineeDto() {
        Trainee trainee = Trainee.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(2000, 1, 1))
                .address("New York")
                .isActive(true)
                .username("john.doe")
                .password("password")
                .build();

        when(traineeService.getProfile("1")).thenReturn(trainee);

        TraineeDto result = gymFacade.getTrainee("1");

        assertNotNull(result);
        assertEquals("John", result.getFirstName());
    }

    @Test
    void getTraining_ShouldReturnTrainingDto() {
        Training training = Training.builder()
                .id("10")
                .trainingName("Morning Yoga")
                .trainingType(new TrainingType("Yoga"))
                .trainerId("2")
                .traineeId("1")
                .trainingDate(LocalDate.of(2025, 6, 10))
                .trainingDuration(Duration.ofMinutes(60))
                .build();

        when(trainingService.getTraining("10")).thenReturn(training);

        TrainingDto result = gymFacade.getTraining("10");

        assertNotNull(result);
        assertEquals("Morning Yoga", result.getTrainingName());
    }

    @Test
    void deleteTrainee_ShouldCallServiceDeleteMethod() {
        String traineeId = "1";

        doNothing().when(traineeService).deleteProfile(traineeId);

        assertDoesNotThrow(() -> gymFacade.deleteTrainee(traineeId));
        verify(traineeService, times(1)).deleteProfile(traineeId);
    }

    @Test
    void getAllTrainees_ShouldReturnListOfTraineeDto() {
        List<Trainee> trainees = List.of(
                Trainee.builder().id("1").firstName("John").lastName("Doe").build(),
                Trainee.builder().id("2").firstName("Jane").lastName("Smith").build()
        );

        when(traineeService.getAllProfiles()).thenReturn(trainees);

        List<TraineeDto> result = gymFacade.getAllTrainees();

        assertEquals(2, result.size());
        assertEquals("John", result.get(0).getFirstName());
        assertEquals("Jane", result.get(1).getFirstName());
    }

    @Test
    void registerTrainer_ShouldCreateAndReturnTrainerDto() {
        TrainerCreateDto dto = new TrainerCreateDto("Alice", "Green", new TrainingType("Yoga"));

        Trainer trainer = Trainer.builder()
                .id("1")
                .firstName("Alice")
                .lastName("Green")
                .specialization(new TrainingType("Yoga"))
                .username("alice.green")
                .password("secure")
                .build();

        when(trainerService.createProfile(any())).thenReturn(trainer);

        TrainerDto result = gymFacade.registerTrainer(dto);

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
        assertEquals("Yoga", result.getSpecialization().getName());
    }

    @Test
    void updateTrainer_ShouldUpdateAndReturnTrainerDto() {
        TrainerUpdateDto dto = new TrainerUpdateDto("1", "Alice", null, null, null);

        Trainer existing = Trainer.builder()
                .id("1")
                .firstName("Alice")
                .lastName("Green")
                .specialization(new TrainingType("Yoga"))
                .isActive(true)
                .username("alice.green")
                .password("secure")
                .build();

        when(trainerService.getProfile("1")).thenReturn(existing);
        when(trainerService.updateProfile(any())).thenReturn(existing);

        TrainerDto result = gymFacade.updateTrainer(dto);

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
    }

    @Test
    void getTrainer_ShouldReturnTrainerDto() {
        Trainer trainer = Trainer.builder()
                .id("1")
                .firstName("Alice")
                .lastName("Green")
                .specialization(new TrainingType("Yoga"))
                .username("alice.green")
                .password("secure")
                .build();

        when(trainerService.getProfile("1")).thenReturn(trainer);

        TrainerDto result = gymFacade.getTrainer("1");

        assertNotNull(result);
        assertEquals("Alice", result.getFirstName());
    }

    @Test
    void getAllTrainers_ShouldReturnListOfTrainerDto() {
        List<Trainer> trainers = List.of(
                Trainer.builder().id("1").firstName("Alice").lastName("Green").build(),
                Trainer.builder().id("2").firstName("Bob").lastName("Brown").build()
        );

        when(trainerService.getAllProfiles()).thenReturn(trainers);

        List<TrainerDto> result = gymFacade.getAllTrainers();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getFirstName());
        assertEquals("Bob", result.get(1).getFirstName());
    }

    @Test
    void getAllTrainings_ShouldReturnListOfTrainingDto() {
        List<Training> trainings = List.of(
                Training.builder().id("1").trainingName("Yoga").trainingType(new TrainingType("Yoga")).build(),
                Training.builder().id("2").trainingName("Boxing").trainingType(new TrainingType("Boxing")).build()
        );

        when(trainingService.getAllTrainings()).thenReturn(trainings);

        List<TrainingDto> result = gymFacade.getAllTrainings();

        assertEquals(2, result.size());
        assertEquals("Yoga", result.get(0).getTrainingName());
        assertEquals("Boxing", result.get(1).getTrainingName());
    }
    @Test
    void getTrainingsByTrainer_ShouldReturnListOfTrainingDto() {
        String trainerId = "5";

        List<Training> trainings = List.of(
                Training.builder().id("1").trainingName("Pilates").trainerId(trainerId).build()
        );

        when(trainingService.findTrainingsByTrainer(trainerId)).thenReturn(trainings);

        List<TrainingDto> result = gymFacade.getTrainingsByTrainer(trainerId);

        assertEquals(1, result.size());
        assertEquals("Pilates", result.getFirst().getTrainingName());
    }

    @Test
    void getTrainingsByTrainee_ShouldReturnListOfTrainingDto() {
        String traineeId = "9";

        List<Training> trainings = List.of(
                Training.builder().id("1").trainingName("CrossFit").traineeId(traineeId).build()
        );

        when(trainingService.findTrainingsByTrainee(traineeId)).thenReturn(trainings);

        List<TrainingDto> result = gymFacade.getTrainingsByTrainee(traineeId);

        assertEquals(1, result.size());
        assertEquals("CrossFit", result.getFirst().getTrainingName());
    }
}

