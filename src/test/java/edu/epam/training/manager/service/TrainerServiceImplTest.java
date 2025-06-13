package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.dto.Credentials;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.service.impl.TrainerServiceImpl;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserService userService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @Test
    void createProfile_success() {
        Trainer input = Trainer.builder()
                .user(User.builder().firstName("John").lastName("Smith").build())
                .specialization(new TrainingType()).build();

        String generatedUsername = "john.smith";
        String generatedPassword = "pass123";

        when(userService.generateUniqueUsername("John", "Smith")).thenReturn(generatedUsername);
        when(passwordGenerator.generate()).thenReturn(generatedPassword);

        ArgumentCaptor<Trainer> trainerCaptor = ArgumentCaptor.forClass(Trainer.class);

        Trainer createdTrainer = Trainer.builder()
                .user(User.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .username(generatedUsername)
                        .password(generatedPassword)
                        .isActive(true)
                        .build())
                .specialization(new TrainingType())
                .build();

        when(trainerDao.create(any())).thenReturn(createdTrainer);

        Trainer result = trainerService.createProfile(input);

        verify(userService).generateUniqueUsername("John", "Smith");
        verify(passwordGenerator).generate();
        verify(trainerDao).create(trainerCaptor.capture());

        Trainer saved = trainerCaptor.getValue();
        assertEquals(generatedUsername, saved.getUser().getUsername());
        assertEquals(generatedPassword, saved.getUser().getPassword());
        assertTrue(saved.getUser().isActive());
        assertEquals(new TrainingType(), saved.getSpecialization());
        assertEquals("John", result.getUser().getFirstName());
    }

    @Test
    void updateProfile_success() {
        String authUser = "admin";
        String authPass = "pass";
        long trainerId = 1L;

        Credentials credentials = new Credentials(authUser, authPass);

        Trainer trainerInput = Trainer.builder()
                .id(trainerId)
                .user(User.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .isActive(false)
                        .build())
                .specialization(new TrainingType())
                .build();

        User existingUser = User.builder()
                .firstName("OldFirst")
                .lastName("OldLast")
                .isActive(true)
                .build();

        Trainer existingTrainer = Trainer.builder()
                .id(trainerId)
                .user(existingUser)
                .specialization(new TrainingType())
                .build();

        when(trainerDao.findById(trainerId)).thenReturn(Optional.of(existingTrainer));
        when(trainerDao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Trainer result = trainerService.updateProfile(credentials, trainerInput);

        verify(authenticationService, times(2)).authenticateCredentials(credentials);
        assertEquals("Jane", result.getUser().getFirstName());
        assertEquals("Doe", result.getUser().getLastName());
        assertFalse(result.getUser().isActive());
        assertEquals(new TrainingType(), result.getSpecialization());
    }

    @Test
    void updateProfile_trainerNotFound_throwsException() {
        String authUser = "admin";
        String authPass = "pass";

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainerDao.findById(99L)).thenReturn(Optional.empty());
        Trainer trainer = Trainer.builder().id(99L).user(User.builder().build()).build();

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class,
                () -> trainerService.updateProfile(credentials, trainer));

        assertTrue(ex.getMessage().contains("Entity with ID '99' not found"));
        verify(trainerDao).findById(99L);
    }

    @Test
    void findUnassignedTrainers_success() {
        String authUser = "admin";
        String authPass = "pass";

        Credentials credentials = new Credentials(authUser, authPass);

        List<Trainer> trainers = List.of(
                Trainer.builder().id(1L).build(),
                Trainer.builder().id(2L).build()
        );

        when(trainerDao.findUnassignedTrainers()).thenReturn(trainers);

        List<Trainer> result = trainerService.findUnassignedTrainers(credentials);

        verify(authenticationService).authenticateCredentials(credentials);
        verify(trainerDao).findUnassignedTrainers();
        assertEquals(2, result.size());
    }

    @Test
    void getTrainerTrainings_success() {
        String authUser = "admin";
        String authPass = "pass";

        Credentials credentials = new Credentials(authUser, authPass);

        String trainerUsername = "trainer1";
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);
        String trainee = "traineeX";

        List<Training> trainings = List.of(
                Training.builder().id(1L).build(),
                Training.builder().id(2L).build()
        );

        when(trainerDao.getTrainerTrainings(trainerUsername, from, to, trainee)).thenReturn(trainings);

        List<Training> result = trainerService.getTrainerTrainings(credentials,
                trainerUsername, from, to, trainee);

        verify(authenticationService).authenticateCredentials(credentials);
        verify(trainerDao).getTrainerTrainings(trainerUsername, from, to, trainee);
        assertEquals(2, result.size());
    }

    @Test
    void findByUsername_success() {
        String authUser = "admin";
        String authPass = "pass";
        String username = "trainer1";

        Credentials credentials = new Credentials(authUser, authPass);

        User user = User.builder()
                .firstName("Alice")
                .lastName("Brown")
                .username(username)
                .password("secret")
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(new TrainingType())
                .build();

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(trainerDao.findByUsername(username)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.findByUsername(credentials, username);

        assertEquals(trainer, result);
        verify(authenticationService).authenticateCredentials(credentials);
        verify(trainerDao).findByUsername(username);
    }

    @Test
    void findByUsername_notFound_throws() {
        String adminUser = "admin";
        String adminPass = "adminPass";
        String username = "notExist";

        Credentials credentials = new Credentials(adminUser, adminPass);

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(trainerDao.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.findByUsername(credentials, username));

        verify(authenticationService).authenticateCredentials(credentials);
        verify(trainerDao).findByUsername(username);
    }

    @Test
    void findById_success() {
        String authUser = "authUser";
        String authPass = "authPass";
        Long id = 42L;

        Credentials credentials = new Credentials(authUser, authPass);

        User user = User.builder()
                .firstName("Emily")
                .lastName("Clark")
                .username("emily.clark")
                .password("pass")
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(new TrainingType())
                .build();

        when(trainerDao.findById(id)).thenReturn(Optional.of(trainer));

        Trainer found = trainerService.findById(credentials, id);

        assertEquals(trainer, found);
        verify(trainerDao).findById(id);
    }

    @Test
    void findById_notFound_throws() {
        String authUser = "authUser";
        String authPass = "authPass";
        Long id = 404L;

        Credentials credentials = new Credentials(authUser, authPass);

        when(trainerDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> trainerService.findById(credentials, id));

        verify(trainerDao).findById(id);
    }

    @Test
    void toggleActiveStatus_success() {
        String adminUser = "admin";
        String adminPass = "adminPass";
        String username = "trainer1";

        Credentials credentials = new Credentials(adminUser, adminPass);

        User user = User.builder()
                .firstName("Tom")
                .lastName("Hardy")
                .username(username)
                .password("secret")
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(new TrainingType())
                .build();

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(trainerDao.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(trainerDao.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.merge(user)).thenReturn(user);

        trainerService.toggleActiveStatus(credentials, username);

        assertFalse(user.isActive());

        verify(authenticationService, times(2)).authenticateCredentials(credentials);
        verify(trainerDao).findByUsername(username);
        verify(session).merge(user);
    }

    @Test
    void changePassword_success() {
        String adminUser = "admin";
        String adminPass = "adminPass";
        String username = "trainer1";

        Credentials credentials = new Credentials(adminUser, adminPass);

        User user = User.builder()
                .firstName("Sam")
                .lastName("Wilson")
                .username(username)
                .password("oldPass")
                .isActive(true)
                .build();

        Trainer trainer = Trainer.builder()
                .user(user)
                .specialization(new TrainingType())
                .build();

        String newPassword = "newStrongPass";

        doNothing().when(authenticationService).authenticateCredentials(credentials);
        when(trainerDao.findByUsername(username)).thenReturn(Optional.of(trainer));
        when(passwordGenerator.generate()).thenReturn(newPassword);
        when(trainerDao.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.merge(user)).thenReturn(user);

        String resultPassword = trainerService.changePassword(credentials, username);

        assertEquals(newPassword, resultPassword);
        assertEquals(newPassword, user.getPassword());

        verify(authenticationService, times(2)).authenticateCredentials(credentials);
        verify(trainerDao).findByUsername(username);
        verify(passwordGenerator).generate();
        verify(session).merge(user);
    }
}
