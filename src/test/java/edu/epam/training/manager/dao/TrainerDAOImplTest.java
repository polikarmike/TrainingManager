package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.impl.TrainerDaoImpl;
import edu.epam.training.manager.domain.Trainer;
import edu.epam.training.manager.storage.impl.TrainerStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDAOImplTest {

    private TrainerDaoImpl trainerDAO;

    @Mock
    private TrainerStorageImpl storageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainerDAO = new TrainerDaoImpl();
        trainerDAO.setStorage(storageMock);
    }

    @Test
    void create_ShouldDelegateToStorageCreate() {
        UUID trainerId = UUID.randomUUID();
        Trainer trainer = new Trainer();
        trainer.setId(trainerId);

        trainerDAO.create(trainer);

        verify(storageMock, times(1)).create(trainer);
    }

    @Test
    void findById_WhenFound_ShouldReturnOptionalWithValue() {
        Trainer t = new Trainer();
        UUID trainerId = UUID.randomUUID();
        t.setId(trainerId);

        when(storageMock.findById(trainerId)).thenReturn(t);

        Optional<Trainer> result = trainerDAO.findById(trainerId);
        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findById(trainerId);
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmptyOptional() {
        UUID trainerId = UUID.randomUUID();
        when(storageMock.findById(trainerId)).thenReturn(null);

        Optional<Trainer> result = trainerDAO.findById(trainerId);
        assertFalse(result.isPresent());
        verify(storageMock).findById(trainerId);
    }



    @Test
    void update_ShouldDelegateToStorageUpdate() {
        Trainer t = new Trainer();
        UUID trainerId = UUID.randomUUID();
        t.setId(trainerId);

        trainerDAO.update(t);

        verify(storageMock).update(t);
    }

    @Test
    void findByUsername_WhenFound_ShouldReturnOptionalWithValue() {
        Trainer t = new Trainer();
        t.setUsername("alexTrainer");

        when(storageMock.findByUsername("alexTrainer")).thenReturn(t);

        Optional<Trainer> result = trainerDAO.findByUsername("alexTrainer");
        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findByUsername("alexTrainer");
    }

    @Test
    void findByUsername_WhenNotFound_ShouldReturnEmptyOptional() {
        when(storageMock.findByUsername("ghost")).thenReturn(null);

        Optional<Trainer> result = trainerDAO.findByUsername("ghost");
        assertFalse(result.isPresent());
        verify(storageMock).findByUsername("ghost");
    }
}
