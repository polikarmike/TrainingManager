package edu.epam.trainingmanager.service;

import edu.epam.trainingmanager.dao.UserDAO;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.domain.TrainingType;
import edu.epam.trainingmanager.service.impl.TrainerServiceImpl;
import edu.epam.trainingmanager.utils.generation.IdGenerator;
import edu.epam.trainingmanager.utils.generation.PasswordGenerator;
import edu.epam.trainingmanager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceImplTest {

    @InjectMocks
    private TrainerServiceImpl trainerService;
    @Mock
    private UserDAO<Trainer> trainerDAO;
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
    void createProfile_ShouldCreateAndReturnTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("Alice");
        trainer.setLastName("Smith");

        trainer.setSpecialization(new TrainingType("Fitness"));

        when(idGenerator.generate()).thenReturn("456");
        when(usernameGenerator.generate(anyString(), anyString(), any())).thenReturn("alice.smith");
        when(passwordGenerator.generate(10)).thenReturn("securePassword");

        Trainer createdTrainer = trainerService.createProfile(trainer);

        assertNotNull(createdTrainer);
        assertEquals("456", createdTrainer.getId());
        assertEquals("alice.smith", createdTrainer.getUsername());
        assertEquals("securePassword", createdTrainer.getPassword());
        assertEquals("Fitness", createdTrainer.getSpecialization().getName());

        verify(trainerDAO, times(1)).create(createdTrainer);
    }

    @Test
    void getProfile_ShouldReturnTrainerIfExists() {
        Trainer trainer = new Trainer();
        trainer.setId("456");

        when(trainerDAO.findById("456")).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getProfile("456");

        assertNotNull(result);
        assertEquals("456", result.getId());
    }

    @Test
    void getProfile_ShouldThrowExceptionIfNotFound() {
        when(trainerDAO.findById("999")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> trainerService.getProfile("999"));
        assertTrue(exception.getMessage().contains("Trainer with ID 999 not found."));
    }

    @Test
    void updateProfile_ShouldUpdateAndReturnTrainer() {
        Trainer trainer = new Trainer();
        trainer.setId("456");

        when(trainerDAO.findById("456")).thenReturn(Optional.of(trainer));

        Trainer updatedTrainer = trainerService.updateProfile(trainer);

        assertNotNull(updatedTrainer);
        assertEquals("456", updatedTrainer.getId());
        verify(trainerDAO, times(1)).update(trainer);
    }

    @Test
    void getAllProfiles_ShouldReturnListOfTrainers() {
        Trainer trainer1 = new Trainer();
        Trainer trainer2 = new Trainer();

        when(trainerDAO.findAll()).thenReturn(List.of(trainer1, trainer2));

        List<Trainer> result = trainerService.getAllProfiles();

        assertEquals(2, result.size());
    }
}

