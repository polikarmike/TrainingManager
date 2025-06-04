package edu.epam.training.manager.dao;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import edu.epam.training.manager.dao.base.AbstractCrudDao;
import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.BaseStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestEntity extends BaseEntity<UUID> {
    private final UUID id;

    public TestEntity(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "TestEntity{" + "id=" + id + '}';
    }
}

interface TestStorage extends BaseStorage<TestEntity, UUID> { }

class TestDAO extends AbstractCrudDao<TestEntity, TestStorage, UUID> { }

public class AbstractCrudDaoTest {

    private TestDAO testDAO;
    private TestStorage testStorage;

    @BeforeEach
    void setUp() {
        testDAO = new TestDAO();
        testStorage = mock(TestStorage.class);
        testDAO.setStorage(testStorage);
    }

    @Test
    void testCreate_success() {
        TestEntity entity = new TestEntity(UUID.randomUUID());
        doNothing().when(testStorage).create(entity);
        assertDoesNotThrow(() -> testDAO.create(entity));
        verify(testStorage).create(entity);
    }

    @Test
    void testCreate_exception() {
        TestEntity entity = new TestEntity(UUID.randomUUID());
        RuntimeException exception = new RuntimeException("create error");
        doThrow(exception).when(testStorage).create(entity);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> testDAO.create(entity));
        assertEquals("create error", thrown.getMessage());
        verify(testStorage).create(entity);
    }

    @Test
    void testFindById_success() {
        UUID id = UUID.randomUUID();
        TestEntity entity = new TestEntity(id);
        when(testStorage.findById(id)).thenReturn(entity);
        Optional<TestEntity> result = testDAO.findById(id);
        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(testStorage).findById(id);
    }

    @Test
    void testFindById_notFound() {
        UUID id = UUID.randomUUID();
        when(testStorage.findById(id)).thenReturn(null);
        Optional<TestEntity> result = testDAO.findById(id);
        assertFalse(result.isPresent());
        verify(testStorage).findById(id);
    }

    @Test
    void testFindById_exception() {
        UUID id = UUID.randomUUID();
        RuntimeException exception = new RuntimeException("find error");
        when(testStorage.findById(id)).thenThrow(exception);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> testDAO.findById(id));
        assertEquals("find error", thrown.getMessage());
        verify(testStorage).findById(id);
    }

    @Test
    void testUpdate_success() {
        TestEntity entity = new TestEntity(UUID.randomUUID());
        doNothing().when(testStorage).update(entity);
        assertDoesNotThrow(() -> testDAO.update(entity));
        verify(testStorage).update(entity);
    }

    @Test
    void testUpdate_exception() {
        TestEntity entity = new TestEntity(UUID.randomUUID());
        RuntimeException exception = new RuntimeException("update error");
        doThrow(exception).when(testStorage).update(entity);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> testDAO.update(entity));
        assertEquals("update error", thrown.getMessage());
        verify(testStorage).update(entity);
    }

    @Test
    void testDelete_success() {
        UUID id = UUID.randomUUID();
        doNothing().when(testStorage).delete(id);
        assertDoesNotThrow(() -> testDAO.delete(id));
        verify(testStorage).delete(id);
    }

    @Test
    void testDelete_exception() {
        UUID id = UUID.randomUUID();
        RuntimeException exception = new RuntimeException("delete error");
        doThrow(exception).when(testStorage).delete(id);
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> testDAO.delete(id));
        assertEquals("delete error", thrown.getMessage());
        verify(testStorage).delete(id);
    }
}
