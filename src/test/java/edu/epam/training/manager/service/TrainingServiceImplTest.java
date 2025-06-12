package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TrainingDao;
import edu.epam.training.manager.domain.*;
import edu.epam.training.manager.exception.InvalidStateException;
import edu.epam.training.manager.service.impl.TrainingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingDao trainingDao;
    @Mock
    private AuthService authService;
    @Mock
    private TraineeService traineeService;
    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Test
    void addTraining_success() {
        String authUser = "admin";
        String authPass = "adminPass";

        Long traineeId = 1L;
        Long trainerId = 2L;

        TrainingType type = new TrainingType();

        Trainee trainee = Trainee.builder()
                .user(User.builder().username("trainee1").build())
                .build();

        Trainer trainer = Trainer.builder()
                .user(User.builder().username("trainer1").build())
                .specialization(type)
                .build();

        Training trainingInput = Training.builder()
                .trainingName("Morning Cardio")
                .trainee(Trainee.builder().id(traineeId).build())
                .trainer(Trainer.builder().id(trainerId).build())
                .trainingDuration(60)
                .trainingDate(LocalDate.of(2025, 6, 1))
                .trainingType(type)
                .build();

        Training expectedTraining = Training.builder()
                .trainee(trainee)
                .trainer(trainer)
                .trainingDuration(60)
                .trainingDate(LocalDate.of(2025, 6, 1))
                .trainingType(type)
                .build();

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeService.findById(traineeId)).thenReturn(trainee);
        when(trainerService.findById(trainerId)).thenReturn(trainer);
        when(trainingDao.create(any(Training.class))).thenReturn(expectedTraining);

        Training result = trainingService.addTraining(authUser, authPass, trainingInput);

        assertEquals(expectedTraining, result);

        verify(authService).authenticateCredentials(authUser, authPass);
        verify(traineeService).findById(traineeId);
        verify(trainerService).findById(trainerId);
        verify(trainingDao).create(any(Training.class));
    }

    @Test
    void addTraining_invalidTrainerSpecialization_throws() {
        String authUser = "admin";
        String authPass = "adminPass";

        Long traineeId = 1L;
        Long trainerId = 2L;

        TrainingType trainingType = new TrainingType();
        ReflectionTestUtils.setField(trainingType, "trainingTypeName", "Yoga");
        TrainingType trainerSpecialization = new TrainingType();
        ReflectionTestUtils.setField(trainerSpecialization, "trainingTypeName", "Boxing");

        Trainee trainee = Trainee.builder()
                .user(User.builder().username("trainee1").build())
                .build();

        Trainer trainer = Trainer.builder()
                .user(User.builder().username("trainer1").build())
                .specialization(trainerSpecialization)
                .build();

        Training trainingInput = Training.builder()
                .trainingName("Strength Session")
                .trainee(Trainee.builder().id(traineeId).build())
                .trainer(Trainer.builder().id(trainerId).build())
                .trainingDuration(45)
                .trainingDate(LocalDate.of(2025, 6, 2))
                .trainingType(trainingType)
                .build();

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeService.findById(traineeId)).thenReturn(trainee);
        when(trainerService.findById(trainerId)).thenReturn(trainer);

        InvalidStateException ex = assertThrows(InvalidStateException.class, () -> trainingService.addTraining(authUser, authPass, trainingInput));

        assertTrue(ex.getMessage().contains("Trainer specialization"));

        verify(authService).authenticateCredentials(authUser, authPass);
        verify(traineeService).findById(traineeId);
        verify(trainerService).findById(trainerId);
        verify(trainingDao, never()).create(any());
    }
}
