package edu.epam.trainingmanager.dao;

import edu.epam.trainingmanager.dao.impl.TrainerDAOImpl;
import edu.epam.trainingmanager.domain.Trainer;
import edu.epam.trainingmanager.storage.UserStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerDAOImplTest {

    private TrainerDAOImpl trainerDAO;

    @Mock
    private UserStorage<Trainer> storageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainerDAO = new TrainerDAOImpl();
        trainerDAO.setStorage(storageMock);
    }

    @Test
    void create_ShouldDelegateToStorageCreate() {
        Trainer trainer = new Trainer();
        trainer.setId("T1");

        trainerDAO.create(trainer);

        verify(storageMock, times(1)).create(trainer);
    }

    @Test
    void findById_WhenFound_ShouldReturnOptionalWithValue() {
        Trainer t = new Trainer();
        t.setId("TTT");

        when(storageMock.findById("TTT")).thenReturn(t);

        Optional<Trainer> result = trainerDAO.findById("TTT");
        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findById("TTT");
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmptyOptional() {
        when(storageMock.findById("none")).thenReturn(null);

        Optional<Trainer> result = trainerDAO.findById("none");
        assertFalse(result.isPresent());
        verify(storageMock).findById("none");
    }

    @Test
    void findAll_ShouldReturnAllItemsFromStorage() {
        Trainer t1 = new Trainer(); t1.setId("1");
        Trainer t2 = new Trainer(); t2.setId("2");
        List<Trainer> list = List.of(t1, t2);

        when(storageMock.findAll()).thenReturn(list);

        Collection<Trainer> result = trainerDAO.findAll();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(list));
        verify(storageMock).findAll();
    }

    @Test
    void update_ShouldDelegateToStorageUpdate() {
        Trainer t = new Trainer();
        t.setId("X");

        trainerDAO.update(t);

        verify(storageMock).update(t);
    }

    @Test
    void delete_ShouldDelegateToStorageDelete() {
        trainerDAO.delete("toRemove");
        verify(storageMock).delete("toRemove");
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

