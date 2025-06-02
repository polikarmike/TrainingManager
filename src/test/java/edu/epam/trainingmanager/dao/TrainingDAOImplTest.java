package edu.epam.trainingmanager.dao;

import edu.epam.trainingmanager.dao.impl.TrainingDAOImpl;
import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.storage.BaseStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingDAOImplTest {

    private TrainingDAOImpl trainingDAO;

    @Mock
    private BaseStorage<Training> storageMock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        trainingDAO = new TrainingDAOImpl();
        trainingDAO.setStorage(storageMock);
    }

    @Test
    void create_ShouldDelegateToStorageCreate() {
        Training tr = new Training();
        tr.setId("TR1");
        tr.setTrainingName("Test");

        trainingDAO.create(tr);

        verify(storageMock, times(1)).create(tr);
    }

    @Test
    void findById_WhenFound_ShouldReturnOptionalWithValue() {
        Training tr = new Training();
        tr.setId("X1");

        when(storageMock.findById("X1")).thenReturn(tr);

        Optional<Training> result = trainingDAO.findById("X1");
        assertTrue(result.isPresent());
        assertSame(tr, result.get());
        verify(storageMock).findById("X1");
    }

    @Test
    void findById_WhenNotFound_ShouldReturnEmptyOptional() {
        when(storageMock.findById("none")).thenReturn(null);

        Optional<Training> result = trainingDAO.findById("none");
        assertFalse(result.isPresent());
        verify(storageMock).findById("none");
    }

    @Test
    void findAll_ShouldReturnAllItemsFromStorage() {
        Training t1 = new Training();
        t1.setId("1");
        t1.setTrainingName("A");
        Training t2 = new Training();
        t2.setId("2");
        t2.setTrainingName("B");
        List<Training> list = List.of(t1, t2);

        when(storageMock.findAll()).thenReturn(list);

        Collection<Training> result = trainingDAO.findAll();
        assertEquals(2, result.size());
        assertTrue(result.containsAll(list));
        verify(storageMock).findAll();
    }

    @Test
    void update_ShouldDelegateToStorageUpdate() {
        Training t = new Training();
        t.setId("Y");

        trainingDAO.update(t);

        verify(storageMock).update(t);
    }

    @Test
    void delete_ShouldDelegateToStorageDelete() {
        trainingDAO.delete("toErase");
        verify(storageMock).delete("toErase");
    }
}

