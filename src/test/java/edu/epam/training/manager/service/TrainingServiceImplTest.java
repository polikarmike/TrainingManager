package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.service.impl.TraineeServiceImpl;
import edu.epam.training.manager.service.impl.TrainerServiceImpl;
import edu.epam.training.manager.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingDao trainingDao;

    @Mock
    private TraineeServiceImpl traineeServiceImpl;

    @Mock
    private TrainerServiceImpl trainerServiceImpl;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Trainee sampleTrainee;
    private Trainer sampleTrainer;

    @BeforeEach
    void setUp() {
        sampleTrainee = Trainee.builder()
                .id(UUID.randomUUID())
                .firstName("Test")
                .lastName("Trainee")
                .dateOfBirth(null)
                .address("Test Address")
                .username("testtrainee")
                .password("password")
                .isActive(true)
                .build();

        sampleTrainer = Trainer.builder()
                .id(UUID.randomUUID())
                .firstName("Test")
                .lastName("Trainer")
                .specialization(TrainingType.YOGA)
                .username("testtrainer")
                .password("password")
                .isActive(true)
                .build();
    }

    @Test
    void testCreateTrainingSuccess() {
        UUID traineeId = sampleTrainee.getId();
        UUID trainerId = sampleTrainer.getId();
        Training input = Training.builder()
                .trainingName("Morning Cardio")
                .trainingType(TrainingType.YOGA)
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingDate(LocalDate.of(2025, 6, 10))
                .build();

        when(traineeServiceImpl.select(traineeId)).thenReturn(sampleTrainee);
        when(trainerServiceImpl.select(trainerId)).thenReturn(sampleTrainer);

        ArgumentCaptor<Training> captor = ArgumentCaptor.forClass(Training.class);
        Training created = trainingService.create(input);

        assertNotNull(created.getId());
        assertEquals("Morning Cardio", created.getTrainingName());

        assertEquals(traineeId, created.getTraineeId());
        assertEquals(trainerId, created.getTrainerId());

        verify(trainingDao, times(1)).create(captor.capture());
        Training passed = captor.getValue();
        assertEquals(created.getId(), passed.getId());
        assertEquals("Morning Cardio", passed.getTrainingName());

    }

    @Test
    void testCreateTrainingTraineeNotFound() {
        UUID traineeId = UUID.randomUUID();
        UUID trainerId = sampleTrainer.getId();
        Training input = Training.builder()
                .trainingName("Morning Cardio")
                .trainingType(TrainingType.YOGA)
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingDate(LocalDate.now())
                .build();

        when(traineeServiceImpl.select(traineeId)).thenThrow(new IllegalArgumentException("Trainee not found"));

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(input));
        verify(trainingDao, never()).create(any());
    }

    @Test
    void testCreateTrainingTrainerNotFound() {
        UUID traineeId = sampleTrainee.getId();
        UUID trainerId = UUID.randomUUID();
        Training input = Training.builder()
                .trainingName("Evening Strength")
                .trainingType(TrainingType.YOGA)
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingDate(LocalDate.now())
                .build();

        when(traineeServiceImpl.select(traineeId)).thenReturn(sampleTrainee);
        when(trainerServiceImpl.select(trainerId)).thenThrow(new IllegalArgumentException("Trainer not found"));

        assertThrows(IllegalArgumentException.class, () -> trainingService.create(input));
        verify(trainingDao, never()).create(any());
    }

    @Test
    void testGetTrainingFound() {
        UUID trainingId = UUID.randomUUID();
        Training sampleTraining = Training.builder()
                .id(trainingId)
                .trainingName("Sample")
                .trainingType(TrainingType.YOGA)
                .traineeId(sampleTrainee.getId())
                .trainerId(sampleTrainer.getId())
                .trainingDate(LocalDate.now())
                .build();

        when(trainingDao.findById(trainingId)).thenReturn(Optional.of(sampleTraining));
        Training result = trainingService.select(trainingId);
        assertEquals(sampleTraining, result);
    }

    @Test
    void testGetTrainingNotFound() {
        UUID trainingId = UUID.randomUUID();
        when(trainingDao.findById(trainingId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> trainingService.select(trainingId));
    }
}
