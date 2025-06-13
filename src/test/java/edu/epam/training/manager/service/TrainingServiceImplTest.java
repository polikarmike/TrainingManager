package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.operations.CreateDao;
import edu.epam.training.manager.domain.*;
import edu.epam.training.manager.dto.Credentials;
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
    private CreateDao<Training, Long> trainingDao;
    @Mock
    private AuthenticationService authenticationService;
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
        Credentials credentials = new Credentials(authUser, authPass);

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

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(traineeService.findById(credentials, traineeId)).thenReturn(trainee);
        when(trainerService.findById(credentials, trainerId)).thenReturn(trainer);
        when(trainingDao.create(any(Training.class))).thenReturn(expectedTraining);

        Training result = trainingService.addTraining(credentials, trainingInput);

        assertEquals(expectedTraining, result);

        verify(authenticationService).authenticateCredentials(credentials);
        verify(traineeService).findById(credentials, traineeId);
        verify(trainerService).findById(credentials, trainerId);
        verify(trainingDao).create(any(Training.class));
    }

    @Test
    void addTraining_invalidTrainerSpecialization_throws() {
        String authUser = "admin";
        String authPass = "adminPass";
        Credentials credentials = new Credentials(authUser, authPass);

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

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(traineeService.findById(credentials, traineeId)).thenReturn(trainee);
        when(trainerService.findById(credentials, trainerId)).thenReturn(trainer);

        InvalidStateException ex = assertThrows(InvalidStateException.class, () -> trainingService.addTraining(credentials, trainingInput));

        assertTrue(ex.getMessage().contains("Trainer specialization"));

        verify(authenticationService).authenticateCredentials(credentials);
        verify(traineeService).findById(credentials, traineeId);
        verify(trainerService).findById(credentials, trainerId);
        verify(trainingDao, never()).create(any());
    }
}
