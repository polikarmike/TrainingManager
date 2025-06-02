package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.dao.UserDAO;
import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.service.impl.TraineeServiceImpl;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

public class TraineeServiceImplTest {

    @InjectMocks
    private TraineeServiceImpl traineeService;
    @Mock
    private UserDAO<Trainee> traineeDAO;
    @Mock
    private UsernameGenerator usernameGenerator;
    @Mock
    private PasswordGenerator passwordGenerator;
    @Mock
    private IdGenerator idGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createProfile_ShouldCreateAndReturnTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        when(idGenerator.generate()).thenReturn("123");
        when(usernameGenerator.generate(anyString(), anyString(), any())).thenReturn("john.doe");
        when(passwordGenerator.generate(10)).thenReturn("securePassword");

        Trainee createdTrainee = traineeService.createProfile(trainee);

        assertNotNull(createdTrainee);
        assertEquals("123", createdTrainee.getId());
        assertEquals("john.doe", createdTrainee.getUsername());
        assertEquals("securePassword", createdTrainee.getPassword());

        verify(traineeDAO, times(1)).create(createdTrainee);
    }
    @Test
    void getProfile_ShouldReturnTraineeIfExists() {
        Trainee trainee = new Trainee();
        trainee.setId("123");

        when(traineeDAO.findById("123")).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getProfile("123");

        assertNotNull(result);
        assertEquals("123", result.getId());
    }

    @Test
    void getProfile_ShouldThrowExceptionIfNotFound() {
        when(traineeDAO.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> traineeService.getProfile("999"));
        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("Trainee with ID 999 not found."));
    }

    @Test
    void updateProfile_ShouldUpdateAndReturnTrainee() {
        Trainee trainee = new Trainee();
        trainee.setId("123");

        when(traineeDAO.findById("123")).thenReturn(Optional.of(trainee));

        Trainee updatedTrainee = traineeService.updateProfile(trainee);

        assertNotNull(updatedTrainee);
        assertEquals("123", updatedTrainee.getId());
        verify(traineeDAO, times(1)).update(trainee);
    }

    @Test
    void deleteProfile_ShouldDeleteTrainee() {
        doNothing().when(traineeDAO).delete("123");

        traineeService.deleteProfile("123");

        verify(traineeDAO, times(1)).delete("123");
    }

    @Test
    void getAllProfiles_ShouldReturnListOfTrainees() {
        Trainee trainee1 = new Trainee();
        Trainee trainee2 = new Trainee();

        when(traineeDAO.findAll()).thenReturn(List.of(trainee1, trainee2));

        List<Trainee> result = traineeService.getAllProfiles();

        assertEquals(2, result.size());
    }
}

