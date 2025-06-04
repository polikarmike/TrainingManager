package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.base.BaseEntity;
import edu.epam.training.manager.storage.base.AbstractStorage;
import edu.epam.training.manager.utils.data.JsonDataLoader;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AbstractStorageInitTest {

    @Setter
    private static class DummyItem extends BaseEntity<UUID> {
        private UUID id;
        @Override
        public UUID getId() { return id; }
    }

    private static class DummyStorage extends AbstractStorage<DummyItem, UUID> {

    }

    private DummyStorage storage;

    @BeforeEach
    void setUp() {
        storage = new DummyStorage();
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
