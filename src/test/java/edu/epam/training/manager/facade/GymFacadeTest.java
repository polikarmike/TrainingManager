package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.service.TraineeService;
import edu.epam.training.manager.service.TrainerService;
import edu.epam.training.manager.service.TrainingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GymFacadeTest {

    @Mock
    private TraineeService traineeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private TrainingService trainingService;

    @InjectMocks
    private GymFacade gymFacade;

    @Test
    void registerTrainee_callsTraineeServiceCreateProfile() {
        Trainee trainee = new Trainee();
        when(traineeService.createProfile(trainee)).thenReturn(trainee);

        Trainee result = gymFacade.registerTrainee(trainee);

        assertEquals(trainee, result);
        verify(traineeService).createProfile(trainee);
    }

    @Test
    void updateTrainee_callsUpdateProfile() {
        String username = "user";
        String password = "pass";
        Trainee traineeUpdate = new Trainee();
        Trainee updated = new Trainee();

        Credentials credentials = new Credentials(username, password);

        when(traineeService.updateProfile(credentials, traineeUpdate)).thenReturn(updated);

        Trainee result = gymFacade.updateTrainee(credentials, traineeUpdate);

        assertEquals(updated, result);
        verify(traineeService).updateProfile(credentials, traineeUpdate);
    }

    @Test
    void deleteTrainee_callsDelete() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "userToDelete";

        Credentials credentials = new Credentials(authUser, authPass);

        doNothing().when(traineeService).delete(credentials, username);

        gymFacade.deleteTrainee(credentials, username);

        verify(traineeService).delete(credentials, username);
    }

    @Test
    void getTraineeByUsername_callsFindByUsername() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";
        Trainee trainee = new Trainee();

        Credentials credentials = new Credentials(authUser, authPass);

        when(traineeService.findByUsername(credentials, username)).thenReturn(trainee);

        Trainee result = gymFacade.getTraineeByUsername(credentials, username);

        assertEquals(trainee, result);
        verify(traineeService).findByUsername(credentials, username);
    }

    @Test
    void getTraineeById_callsFindById() {
        String authUser = "authUser";
        String authPass = "authPass";
        Long id = 123L;
        Trainee trainee = new Trainee();

        Credentials credentials = new Credentials(authUser, authPass);

        when(traineeService.findById(credentials, id)).thenReturn(trainee);

        Trainee result = gymFacade.getTraineeById(credentials, id);

        assertEquals(trainee, result);
        verify(traineeService).findById(credentials, id);
    }

    @Test
    void toggleTraineeStatus_callsToggleActiveStatus() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";

        Credentials credentials = new Credentials(authUser, authPass);

        doNothing().when(traineeService).toggleActiveStatus(credentials, username);

        gymFacade.toggleTraineeStatus(credentials, username);

        verify(traineeService).toggleActiveStatus(credentials, username);
    }

    @Test
    void getTraineeTrainings_callsGetTraineeTrainings() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";
        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 12, 31);
        String trainerUsername = "trainer1";
        String trainingType = "CARDIO";

        Credentials credentials = new Credentials(authUser, authPass);

        List<Training> expectedTrainings = List.of(new Training());

        when(traineeService.getTraineeTrainings(credentials, username, fromDate, toDate, trainerUsername, trainingType))
                .thenReturn(expectedTrainings);

        List<Training> result = gymFacade.getTraineeTrainings(credentials, username, fromDate, toDate, trainerUsername, trainingType);

        assertEquals(expectedTrainings, result);
        verify(traineeService).getTraineeTrainings(credentials, username, fromDate, toDate, trainerUsername, trainingType);
    }

    @Test
    void changeTraineePassword_callsChangePassword() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";
        String newPassword = "newPass";

        Credentials credentials = new Credentials(authUser, authPass);

        when(traineeService.changePassword(credentials, username)).thenReturn(newPassword);

        String result = gymFacade.changeTraineePassword(credentials, username);

        assertEquals(newPassword, result);
        verify(traineeService).changePassword(credentials, username);
    }

    @Test
    void registerTrainer_callsTrainerServiceCreateProfile() {
        Trainer trainer = new Trainer();
        when(trainerService.createProfile(trainer)).thenReturn(trainer);

        Trainer result = gymFacade.registerTrainer(trainer);

        assertEquals(trainer, result);
        verify(trainerService).createProfile(trainer);
    }

    @Test
    void updateTrainer_callsUpdateProfile() {
        String username = "trainerUser";
        String password = "pass";
        Trainer trainerUpdate = new Trainer();
        Trainer updated = new Trainer();

        Credentials credentials = new Credentials(username, password);

        when(trainerService.updateProfile(credentials, trainerUpdate)).thenReturn(updated);

        Trainer result = gymFacade.updateTrainer(credentials, trainerUpdate);

        assertEquals(updated, result);
        verify(trainerService).updateProfile(credentials, trainerUpdate);
    }

    @Test
    void getTrainerByUsername_callsFindByUsername() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        Trainer trainer = new Trainer();

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainerService.findByUsername(credentials, username)).thenReturn(trainer);

        Trainer result = gymFacade.getTrainerByUsername(credentials, username);

        assertEquals(trainer, result);
        verify(trainerService).findByUsername(credentials, username);
    }

    @Test
    void getTrainerById_callsFindById() {
        String authUser = "authUser";
        String authPass = "authPass";
        Long id = 123L;
        Trainer trainer = new Trainer();

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainerService.findById(credentials, id)).thenReturn(trainer);

        Trainer result = gymFacade.getTrainerById(credentials, id);

        assertEquals(trainer, result);
        verify(trainerService).findById(credentials, id);
    }

    @Test
    void toggleTrainerStatus_callsToggleActiveStatus() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainerUser";

        Credentials credentials = new Credentials(authUser, authPass);

        doNothing().when(trainerService).toggleActiveStatus(credentials, username);

        gymFacade.toggleTrainerStatus(credentials, username);

        verify(trainerService).toggleActiveStatus(credentials, username);
    }

    @Test
    void getTrainerTrainings_callsGetTrainerTrainings() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 12, 31);
        String traineeUsername = "trainee1";

        Credentials credentials = new Credentials(authUser, authPass);

        List<Training> expectedTrainings = List.of(new Training());

        when(trainerService.getTrainerTrainings(credentials, username, fromDate, toDate, traineeUsername))
                .thenReturn(expectedTrainings);

        List<Training> result = gymFacade.getTrainerTrainings(credentials, username, fromDate, toDate, traineeUsername);

        assertEquals(expectedTrainings, result);
        verify(trainerService).getTrainerTrainings(credentials, username, fromDate, toDate, traineeUsername);
    }

    @Test
    void changeTrainerPassword_callsChangePassword() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        String newPassword = "newPass";

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainerService.changePassword(credentials, username)).thenReturn(newPassword);

        String result = gymFacade.changeTrainerPassword(credentials, username);

        assertEquals(newPassword, result);
        verify(trainerService).changePassword(credentials, username);
    }

    @Test
    void addTraining_callsTrainingServiceAddTraining() {
        String authUser = "authUser";
        String authPass = "authPass";
        Training training = new Training();
        Training createdTraining = new Training();

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainingService.addTraining(credentials, training)).thenReturn(createdTraining);

        Training result = gymFacade.addTraining(credentials, training);

        assertEquals(createdTraining, result);
        verify(trainingService).addTraining(credentials, training);
    }
}
