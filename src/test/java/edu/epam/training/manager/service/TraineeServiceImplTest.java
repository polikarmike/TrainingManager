package edu.epam.training.manager.service;

import edu.epam.training.manager.dao.TraineeDao;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.service.impl.TraineeServiceImpl;
import edu.epam.training.manager.utils.generation.PasswordGenerator;
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
class TraineeServiceImplTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserService userService;

    @Mock
    private PasswordGenerator passwordGenerator;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    private Trainee sampleTrainee;

    @BeforeEach
    void setUp() {
        sampleTrainee = Trainee.builder()
                .id(UUID.randomUUID())
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .username("johndoe")
                .password("secret")
                .isActive(true)
                .build();
    }

    @Test
    void testCreate() {
        Trainee input = Trainee.builder()
                .firstName("Alice")
                .lastName("Smith")
                .dateOfBirth(LocalDate.of(1995, 5, 15))
                .address("456 Elm St")
                .build();
        when(userService.generateUniqueUsername("Alice", "Smith")).thenReturn("alicesmith");
        when(passwordGenerator.generate()).thenReturn("pw1234");
        ArgumentCaptor<Trainee> captor = ArgumentCaptor.forClass(Trainee.class);
        Trainee created = traineeService.create(input);
        assertNotNull(created.getId());
        assertEquals("Alice", created.getFirstName());
        assertEquals("Smith", created.getLastName());
        assertEquals(LocalDate.of(1995, 5, 15), created.getDateOfBirth());
        assertEquals("456 Elm St", created.getAddress());
        assertEquals("alicesmith", created.getUsername());
        assertEquals("pw1234", created.getPassword());
        verify(traineeDao, times(1)).create(captor.capture());
        Trainee passed = captor.getValue();
        assertEquals(created.getId(), passed.getId());
        assertEquals("alicesmith", passed.getUsername());
        assertEquals("pw1234", passed.getPassword());
    }

    @Test
    void testSelectFound() {
        UUID id = sampleTrainee.getId();
        when(traineeDao.findById(id)).thenReturn(Optional.of(sampleTrainee));
        Trainee result = traineeService.select(id);
        assertEquals(sampleTrainee, result);
    }

    @Test
    void testSelectNotFound() {
        UUID id = UUID.randomUUID();
        when(traineeDao.findById(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> traineeService.select(id));
    }

    @Test
    void testUpdate() {
        UUID id = sampleTrainee.getId();
        Trainee update = Trainee.builder()
                .id(id)
                .firstName("Jane")
                .lastName(null)
                .dateOfBirth(null)
                .address(null)
                .isActive(true)
                .build();
        when(traineeDao.findById(id)).thenReturn(Optional.of(sampleTrainee));
        Trainee merged = Trainee.builder()
                .id(id)
                .firstName("Jane")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .address("123 Main St")
                .username("johndoe")
                .password("secret")
                .isActive(true)
                .build();
        doNothing().when(traineeDao).update(any());
        when(traineeDao.findById(id)).thenReturn(Optional.of(merged));
        Trainee result = traineeService.update(update);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), result.getDateOfBirth());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("johndoe", result.getUsername());
        assertEquals("secret", result.getPassword());
        assertTrue(result.isActive());
        verify(traineeDao, times(1)).update(any());
    }

    @Test
    void testDelete() {
        UUID id = UUID.randomUUID();
        doNothing().when(traineeDao).delete(id);
        traineeService.delete(id);
        verify(traineeDao, times(1)).delete(id);
    }
}
