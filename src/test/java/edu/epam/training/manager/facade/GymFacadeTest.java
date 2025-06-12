package edu.epam.training.manager.facade;

import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
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

        when(traineeService.updateProfile(username, password, traineeUpdate)).thenReturn(updated);

        Trainee result = gymFacade.updateTrainee(username, password, traineeUpdate);

        assertEquals(updated, result);
        verify(traineeService).updateProfile(username, password, traineeUpdate);
    }

    @Test
    void deleteTrainee_callsDelete() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "userToDelete";

        doNothing().when(traineeService).delete(authUser, authPass, username);

        gymFacade.deleteTrainee(authUser, authPass, username);

        verify(traineeService).delete(authUser, authPass, username);
    }

    @Test
    void getTraineeByUsername_callsFindByUsername() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";
        Trainee trainee = new Trainee();

        when(traineeService.findByUsername(authUser, authPass, username)).thenReturn(trainee);

        Trainee result = gymFacade.getTraineeByUsername(authUser, authPass, username);

        assertEquals(trainee, result);
        verify(traineeService).findByUsername(authUser, authPass, username);
    }

    @Test
    void getTraineeById_callsFindById() {
        Long id = 123L;
        Trainee trainee = new Trainee();

        when(traineeService.findById(id)).thenReturn(trainee);

        Trainee result = gymFacade.getTraineeById(id);

        assertEquals(trainee, result);
        verify(traineeService).findById(id);
    }

    @Test
    void toggleTraineeStatus_callsToggleActiveStatus() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";

        doNothing().when(traineeService).toggleActiveStatus(authUser, authPass, username);

        gymFacade.toggleTraineeStatus(authUser, authPass, username);

        verify(traineeService).toggleActiveStatus(authUser, authPass, username);
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

        List<Training> expectedTrainings = List.of(new Training());

        when(traineeService.getTraineeTrainings(authUser, authPass, username, fromDate, toDate, trainerUsername, trainingType))
                .thenReturn(expectedTrainings);

        List<Training> result = gymFacade.getTraineeTrainings(authUser, authPass, username, fromDate, toDate, trainerUsername, trainingType);

        assertEquals(expectedTrainings, result);
        verify(traineeService).getTraineeTrainings(authUser, authPass, username, fromDate, toDate, trainerUsername, trainingType);
    }

    @Test
    void changeTraineePassword_callsChangePassword() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "user1";
        String newPassword = "newPass";

        when(traineeService.changePassword(authUser, authPass, username)).thenReturn(newPassword);

        String result = gymFacade.changeTraineePassword(authUser, authPass, username);

        assertEquals(newPassword, result);
        verify(traineeService).changePassword(authUser, authPass, username);
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

        when(trainerService.updateProfile(username, password, trainerUpdate)).thenReturn(updated);

        Trainer result = gymFacade.updateTrainer(username, password, trainerUpdate);

        assertEquals(updated, result);
        verify(trainerService).updateProfile(username, password, trainerUpdate);
    }

    @Test
    void getTrainerByUsername_callsFindByUsername() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        Trainer trainer = new Trainer();

        when(trainerService.findByUsername(authUser, authPass, username)).thenReturn(trainer);

        Trainer result = gymFacade.getTrainerByUsername(authUser, authPass, username);

        assertEquals(trainer, result);
        verify(trainerService).findByUsername(authUser, authPass, username);
    }

    @Test
    void getTrainerById_callsFindById() {
        Long id = 123L;
        Trainer trainer = new Trainer();

        when(trainerService.findById(id)).thenReturn(trainer);

        Trainer result = gymFacade.getTrainerById(id);

        assertEquals(trainer, result);
        verify(trainerService).findById(id);
    }

    @Test
    void toggleTrainerStatus_callsToggleActiveStatus() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainerUser";

        doNothing().when(trainerService).toggleActiveStatus(authUser, authPass, username);

        gymFacade.toggleTrainerStatus(authUser, authPass, username);

        verify(trainerService).toggleActiveStatus(authUser, authPass, username);
    }

    @Test
    void getTrainerTrainings_callsGetTrainerTrainings() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        LocalDate fromDate = LocalDate.of(2025, 1, 1);
        LocalDate toDate = LocalDate.of(2025, 12, 31);
        String traineeUsername = "trainee1";

        List<Training> expectedTrainings = List.of(new Training());

        when(trainerService.getTrainerTrainings(authUser, authPass, username, fromDate, toDate, traineeUsername))
                .thenReturn(expectedTrainings);

        List<Training> result = gymFacade.getTrainerTrainings(authUser, authPass, username, fromDate, toDate, traineeUsername);

        assertEquals(expectedTrainings, result);
        verify(trainerService).getTrainerTrainings(authUser, authPass, username, fromDate, toDate, traineeUsername);
    }

    @Test
    void changeTrainerPassword_callsChangePassword() {
        String authUser = "authUser";
        String authPass = "authPass";
        String username = "trainer1";
        String newPassword = "newPass";

        when(trainerService.changePassword(authUser, authPass, username)).thenReturn(newPassword);

        String result = gymFacade.changeTrainerPassword(authUser, authPass, username);

        assertEquals(newPassword, result);
        verify(trainerService).changePassword(authUser, authPass, username);
    }

    @Test
    void addTraining_callsTrainingServiceAddTraining() {
        String authUser = "authUser";
        String authPass = "authPass";
        Training training = new Training();
        Training createdTraining = new Training();

        when(trainingService.addTraining(authUser, authPass, training)).thenReturn(createdTraining);

        Training result = gymFacade.addTraining(authUser, authPass, training);

        assertEquals(createdTraining, result);
        verify(trainingService).addTraining(authUser, authPass, training);
    }
}
