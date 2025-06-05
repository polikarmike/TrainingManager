package edu.epam.training.manager.dao;

import edu.epam.training.manager.dao.impl.TraineeDaoImpl;
import edu.epam.training.manager.domain.Trainee;
import edu.epam.training.manager.storage.impl.TraineeStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraineeDAOImplTest {

    private TraineeDaoImpl traineeDAO;

    @Mock
    private TraineeStorageImpl storageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeDAO = new TraineeDaoImpl();
        traineeDAO.setStorage(storageMock);
    }

    @Test
    void create_ShouldDelegateToStorageCreate() {
        Trainee t = new Trainee();
        t.setId(UUID.randomUUID());
        t.setUsername("user1");

        traineeDAO.create(t);

        verify(storageMock, times(1)).create(t);
    }

    @Test
    void findById_WhenFound_ShouldReturnOptionalWithValue() {
        Trainee t = new Trainee();
        UUID id = UUID.randomUUID();

        t.setId(id);

        when(storageMock.findById(id)).thenReturn(t);

        Optional<Trainee> result = traineeDAO.findById(id);

        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findById(id);
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmptyOptional() {
        UUID id = UUID.randomUUID();
        when(storageMock.findById(id)).thenReturn(null);

        Optional<Trainee> result = traineeDAO.findById(id);

        assertFalse(result.isPresent());
        verify(storageMock).findById(id);
    }


    @Test
    void update_ShouldDelegateToStorageUpdate() {
        Trainee t = new Trainee();
        t.setId(UUID.randomUUID());

        traineeDAO.update(t);

        verify(storageMock).update(t);
    }

    @Test
    void delete_ShouldDelegateToStorageDelete() {
        UUID id = UUID.randomUUID();
        traineeDAO.delete(id);

        verify(storageMock).delete(id);
    }

    @Test
    void findByUsername_WhenFound_ShouldReturnOptionalWithValue() {
        Trainee t = new Trainee();
        t.setUsername("john123");

        when(storageMock.findByUsername("john123")).thenReturn(t);

        Optional<Trainee> result = traineeDAO.findByUsername("john123");

        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findByUsername("john123");
    }

    @Test
    void findByUsername_WhenNotFound_ShouldReturnEmptyOptional() {
        when(storageMock.findByUsername("nobody")).thenReturn(null);

        Optional<Trainee> result = traineeDAO.findByUsername("nobody");

        assertFalse(result.isPresent());
        verify(storageMock).findByUsername("nobody");
    }
}
