package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.domain.User;
import edu.epam.training.manager.exception.EntityNotFoundException;
import edu.epam.training.manager.service.impl.TraineeServiceImpl;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TraineeServiceImplTest {

    @Mock
    private AuthService authService;

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserService userService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private TraineeServiceImpl spyTraineeService;

    @BeforeEach
    void setup() {
        spyTraineeService = Mockito.spy(traineeService);
    }

    @Test
    void createProfile_success() {
        Trainee trainee = Trainee.builder()
                .user(User.builder().firstName("John").lastName("Doe").build())
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Street").build();

        String generatedUsername = "john.doe";
        String generatedPassword = "pass123";

        when(userService.generateUniqueUsername("John", "Doe")).thenReturn(generatedUsername);
        when(passwordGenerator.generate()).thenReturn(generatedPassword);
        when(traineeDao.create(any(Trainee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Trainee created = traineeService.createProfile(trainee);

        assertEquals("John", created.getUser().getFirstName());
        assertEquals(generatedUsername, created.getUser().getUsername());
        assertEquals(generatedPassword, created.getUser().getPassword());
    }

    @Test
    void updateProfile_success() {
        User user = User.builder().firstName("Jane").lastName("Smith").build();
        Trainee existing = Trainee.builder().id(1L).user(user).build();

        Trainee updatedTrainee = Trainee.builder()
                .id(1L)
                .user(User.builder().firstName("UpdatedName").build())
                .build();

        when(traineeDao.findById(1L)).thenReturn(Optional.of(existing));
        when(traineeDao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Trainee result = traineeService.updateProfile("authUser", "authPass", updatedTrainee);

        assertEquals("UpdatedName", result.getUser().getFirstName());
    }

    @Test
    void updateProfile_notFound_throwsException() {
        when(traineeDao.findById(1L)).thenReturn(Optional.empty());

        Trainee updatedTrainee = Trainee.builder().id(1L).build();

        assertThrows(EntityNotFoundException.class, () ->
                traineeService.updateProfile("authUser", "authPass", updatedTrainee));
    }

    @Test
    void delete_success() {
        String authUsername = "authUser";
        String authPassword = "authPass";
        String usernameToDelete = "targetUser";

        Trainee trainee = new Trainee();
        trainee.setId(123L);

        doNothing().when(authService).authenticateCredentials(authUsername, authPassword);

        doReturn(trainee).when(spyTraineeService).findByUsername(authUsername, authPassword, usernameToDelete);

        doNothing().when(traineeDao).delete(trainee.getId());

        spyTraineeService.delete(authUsername, authPassword, usernameToDelete);

        verify(authService).authenticateCredentials(authUsername, authPassword);
        verify(spyTraineeService).findByUsername(authUsername, authPassword, usernameToDelete);
        verify(traineeDao).delete(trainee.getId());
    }

    @Test
    void getTraineeTrainings_success() {
        List<Training> mockTrainings = Collections.singletonList(mock(Training.class));

        when(traineeDao.getTraineeTrainings("trainee1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                "trainer1", "Cardio"))
                .thenReturn(mockTrainings);

        List<Training> result = traineeService.getTraineeTrainings(
                "authUser", "authPass", "trainee1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                "trainer1", "Cardio");

        assertEquals(1, result.size());
    }

    @Test
    void findByUsername_success() {
        String authUser = "admin";
        String authPass = "adminPass";
        String username = "user1";

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username(username)
                .password("pass")
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Street")
                .build();

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeDao.findByUsername(username)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.findByUsername(authUser, authPass, username);

        assertEquals(trainee, result);
        verify(authService).authenticateCredentials(authUser, authPass);
        verify(traineeDao).findByUsername(username);
    }

    @Test
    void findByUsername_notFound_throws() {
        String authUser = "admin";
        String authPass = "adminPass";
        String username = "notExist";

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeDao.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.findByUsername(authUser, authPass, username));

        verify(authService).authenticateCredentials(authUser, authPass);
        verify(traineeDao).findByUsername(username);
    }

    @Test
    void findById_success() {
        Long id = 123L;

        User user = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .username("jane.smith")
                .password("pass")
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1985, 5, 20))
                .address("456 Avenue")
                .build();

        when(traineeDao.findById(id)).thenReturn(Optional.of(trainee));

        Trainee found = traineeService.findById(id);

        assertEquals(trainee, found);
        verify(traineeDao).findById(id);
    }

    @Test
    void findById_notFound_throws() {
        Long id = 999L;
        when(traineeDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> traineeService.findById(id));

        verify(traineeDao).findById(id);
    }

    @Test
    void toggleActiveStatus_success() {
        String authUser = "admin";
        String authPass = "adminPass";
        String username = "user1";

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username(username)
                .password("pass")
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Street")
                .build();

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeDao.findByUsername(username)).thenReturn(Optional.of(trainee));

        when(session.merge(user)).thenReturn(user);

        when(traineeDao.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        traineeService.toggleActiveStatus(authUser, authPass, username);

        assertFalse(user.isActive());

        verify(authService, times(2)).authenticateCredentials(authUser, authPass);
        verify(traineeDao).findByUsername(username);
        verify(session).merge(user);
    }

    @Test
    void changePassword_success() {
        String authUser = "admin";
        String authPass = "adminPass";
        String username = "user1";

        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .username(username)
                .password("oldPass")
                .isActive(true)
                .build();

        Trainee trainee = Trainee.builder()
                .user(user)
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Street")
                .build();

        doNothing().when(authService).authenticateCredentials(authUser, authPass);
        when(traineeDao.findByUsername(username)).thenReturn(Optional.of(trainee));

        String newPassword = "newSecret123";
        when(passwordGenerator.generate()).thenReturn(newPassword);
        when(session.merge(user)).thenReturn(user);

        when(traineeDao.getSessionFactory()).thenReturn(sessionFactory);
        when(sessionFactory.getCurrentSession()).thenReturn(session);

        String returnedPassword = traineeService.changePassword(authUser, authPass, username);

        assertEquals(newPassword, returnedPassword);
        assertEquals(newPassword, user.getPassword());

        verify(authService, times(2)).authenticateCredentials(authUser, authPass);
        verify(traineeDao).findByUsername(username);
        verify(passwordGenerator).generate();
        verify(session).merge(user);
    }
}
