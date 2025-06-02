package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TrainerDao;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.domain.TrainingType;
import edu.epam.training.manager.service.impl.TrainerServiceImpl;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainerServiceImplTest {

    @Mock
    private TrainerDao trainerDao;

    @Mock
    private UserService userService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer sampleTrainer;

    @BeforeEach
    void setUp() {
        sampleTrainer = Trainer.builder()
                .id(UUID.randomUUID())
                .firstName("Max")
                .lastName("Payne")
                .specialization(TrainingType.YOGA)
                .username("maxpayne")
                .password("pass123")
                .isActive(true)
                .build();
    }

    @Test
    void testCreateProfile() {
        Trainer input = Trainer.builder()
                .firstName("Laura")
                .lastName("Croft")
                .specialization(TrainingType.YOGA)
                .build();
        when(userService.generateUniqueUsername("Laura", "Croft")).thenReturn("lauracroft");
        when(passwordGenerator.generate()).thenReturn("securePass");
        ArgumentCaptor<Trainer> captor = ArgumentCaptor.forClass(Trainer.class);
        Trainer created = trainerService.createProfile(input);
        assertNotNull(created.getId());
        assertEquals("Laura", created.getFirstName());
        assertEquals("Croft", created.getLastName());
        assertEquals("lauracroft", created.getUsername());
        assertEquals("securePass", created.getPassword());
        verify(trainerDao, times(1)).create(captor.capture());
        Trainer passed = captor.getValue();
        assertEquals(created.getId(), passed.getId());
        assertEquals("lauracroft", passed.getUsername());
        assertEquals("securePass", passed.getPassword());
    }

    @Test
    void testGetProfileFound() {
        UUID id = sampleTrainer.getId();
        when(trainerDao.findById(id)).thenReturn(Optional.of(sampleTrainer));
        Trainer result = trainerService.getProfile(id);
        assertEquals(sampleTrainer, result);
    }

    @Test
    void testGetProfileNotFound() {
        UUID id = UUID.randomUUID();
        when(trainerDao.findById(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> trainerService.getProfile(id));
    }

    @Test
    void testUpdateProfile() {
        UUID id = sampleTrainer.getId();
        Trainer update = Trainer.builder()
                .id(id)
                .firstName("Marcus")
                .lastName(null)
                .specialization(TrainingType.YOGA)
                .isActive(false)
                .build();
        when(trainerDao.findById(id)).thenReturn(Optional.of(sampleTrainer));
        Trainer merged = Trainer.builder()
                .id(id)
                .firstName("Marcus")
                .lastName("Payne")
                .specialization(TrainingType.YOGA)
                .username("maxpayne")
                .password("pass123")
                .isActive(false)
                .build();
        doNothing().when(trainerDao).update(any());
        when(trainerDao.findById(id)).thenReturn(Optional.of(merged));
        Trainer result = trainerService.updateProfile(update);
        assertEquals("Marcus", result.getFirstName());
        assertEquals("Payne", result.getLastName());
        assertEquals("maxpayne", result.getUsername());
        assertEquals("pass123", result.getPassword());
        assertFalse(result.isActive());
        verify(trainerDao, times(1)).update(any());
    }
}
