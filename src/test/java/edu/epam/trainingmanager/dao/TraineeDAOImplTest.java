package edu.epam.trainingmanager.dao;

import edu.epam.trainingmanager.dao.impl.TraineeDAOImpl;
import edu.epam.trainingmanager.domain.Trainee;
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

class TraineeDAOImplTest {

    private TraineeDAOImpl traineeDAO;

    @Mock
    private UserStorage<Trainee> storageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        traineeDAO = new TraineeDAOImpl();
        traineeDAO.setStorage(storageMock);
    }

    @Test
    void create_ShouldDelegateToStorageCreate() {
        Trainee t = new Trainee();
        t.setId("123");
        t.setUsername("user1");

        traineeDAO.create(t);

        verify(storageMock, times(1)).create(t);
    }

    @Test
    void findById_WhenFound_ShouldReturnOptionalWithValue() {
        Trainee t = new Trainee();
        t.setId("abc");

        when(storageMock.findById("abc")).thenReturn(t);

        Optional<Trainee> result = traineeDAO.findById("abc");

        assertTrue(result.isPresent());
        assertSame(t, result.get());
        verify(storageMock).findById("abc");
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmptyOptional() {
        when(storageMock.findById("doesNotExist")).thenReturn(null);

        Optional<Trainee> result = traineeDAO.findById("doesNotExist");

        assertFalse(result.isPresent());
        verify(storageMock).findById("doesNotExist");
    }

    @Test
    void findAll_ShouldReturnAllItemsFromStorage() {
        Trainee t1 = new Trainee(); t1.setId("1");
        Trainee t2 = new Trainee(); t2.setId("2");
        List<Trainee> list = List.of(t1, t2);

        when(storageMock.findAll()).thenReturn(list);

        Collection<Trainee> result = traineeDAO.findAll();

        assertEquals(2, result.size());
        assertTrue(result.contains(t1));
        assertTrue(result.contains(t2));
        verify(storageMock).findAll();
    }

    @Test
    void update_ShouldDelegateToStorageUpdate() {
        Trainee t = new Trainee();
        t.setId("xyz");

        traineeDAO.update(t);

        verify(storageMock).update(t);
    }

    @Test
    void delete_ShouldDelegateToStorageDelete() {
        traineeDAO.delete("toDelete");

        verify(storageMock).delete("toDelete");
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

