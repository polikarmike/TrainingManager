package edu.epam.trainingmanager.Storage;

import edu.epam.trainingmanager.domain.common.Identifiable;
import edu.epam.trainingmanager.storage.base.AbstractStorage;
import edu.epam.trainingmanager.utils.data.JsonDataLoader;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStorageInitTest {

    @Setter
    private static class DummyItem implements Identifiable {
        private String id;
        @Override
        public String getId() { return id; }
    }

    private static class DummyStorage extends AbstractStorage<DummyItem> {

    }

    private DummyStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DummyStorage();
    }

    @Test
    void init_whenInitFilePathNull_shouldSkipInitialization() {

        assertDoesNotThrow(() -> storage.init());
        assertTrue(storage.findAll().isEmpty());
    }

    @Test
    void init_whenInitFilePathEmpty_shouldSkipInitialization() {
        storage.setInitFilePath("   ");
        storage.setItemClass(DummyItem.class);
        assertDoesNotThrow(() -> storage.init());
        assertTrue(storage.findAll().isEmpty());
    }

    @Test
    void init_whenItemClassNotSetAndInitFilePathProvided_shouldThrowIllegalStateException() {
        storage.setInitFilePath("some.json");
        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> storage.init(),
                "If itemClass == null with initFilePath specified, should throw IllegalStateException"
        );
        assertTrue(ex.getMessage().contains("itemClass is not set"));
    }

    @Test
    void init_whenResourceNotFound_shouldThrowIllegalStateException() {
        storage.setInitFilePath("nonexistent-file.json");
        storage.setItemClass(DummyItem.class);

        storage.setJsonDataLoader(new JsonDataLoader() {
            @Override
            public <U> java.util.List<U> loadData(java.io.InputStream is, Class<U> clazz) {
                fail("JsonDataLoader should not be called if file is not found");
                return null;
            }
        });

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> storage.init(),
                "If the resource is not found, an IllegalStateException should be thrown"
        );
        Throwable cause = ex.getCause();
        assertNotNull(cause);
        assertInstanceOf(FileNotFoundException.class, cause, "Expected FileNotFoundException as the cause");
        assertTrue(ex.getMessage().contains("Failed to initialize storage"));
    }
}

