package edu.epam.training.manager.storage;

import edu.epam.training.manager.domain.Training;
import edu.epam.training.manager.storage.impl.TrainingStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainingStorageTest {

    private TrainingStorageImpl storage;

    @BeforeEach
    void setUp() {
        storage = new TrainingStorageImpl();
    }

    @Test
    void create_and_findById_shouldStoreAndRetrieveItem() {
        UUID id = UUID.randomUUID();

        Training t = new Training();
        t.setId(id);
        t.setTrainingName("Yoga");
        t.setTrainingDate(LocalDate.of(2025, 6, 10));
        t.setTrainingDuration(Duration.ofMinutes(45));

        storage.create(t);

        Training fetched = storage.findById(id);
        assertNotNull(fetched, "findById вернул null после create");
        assertEquals("Yoga", fetched.getTrainingName());
        assertEquals(Duration.ofMinutes(45), fetched.getTrainingDuration());
    }

    @Test
    void findById_whenNotExists_shouldReturnNull() {
        Training fetched = storage.findById(UUID.randomUUID());
        assertNull(fetched, "findById on a non-existent ID should return null");
    }


    @Test
    void update_shouldOverwriteExistingItem() {
        UUID id = UUID.randomUUID();

        Training t = new Training();
        t.setId(id);
        t.setTrainingName("CrossFit");
        storage.create(t);

        Training updated = new Training();
        updated.setId(id);
        updated.setTrainingName("CrossFit Pro");
        updated.setTrainingDate(LocalDate.of(2025, 7, 1));
        updated.setTrainingDuration(Duration.ofMinutes(60));

        storage.update(updated);

        Training fetched = storage.findById(id);
        assertNotNull(fetched);
        assertEquals("CrossFit Pro", fetched.getTrainingName());
        assertEquals(LocalDate.of(2025, 7, 1), fetched.getTrainingDate());
        assertEquals(Duration.ofMinutes(60), fetched.getTrainingDuration());
    }

    @Test
    void delete_shouldRemoveItem() {
        UUID id = UUID.randomUUID();

        Training t = new Training();
        t.setId(id);
        storage.create(t);

        assertNotNull(storage.findById(id), "The object must exist before deletion.");
        storage.delete(id);
        assertNull(storage.findById(id), "After delete the object must be deleted");
    }
}