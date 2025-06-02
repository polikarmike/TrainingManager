package edu.epam.trainingmanager.Storage;

import edu.epam.trainingmanager.domain.Training;
import edu.epam.trainingmanager.storage.impl.TrainingStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrainingStorageTest {

    private TrainingStorage storage;

    @BeforeEach
    void setUp() {
        storage = new TrainingStorage();
    }

    @Test
    void create_and_findById_shouldStoreAndRetrieveItem() {
        Training t = new Training();
        t.setId("T1");
        t.setTrainingName("Yoga");
        t.setTrainingDate(LocalDate.of(2025, 6, 10));
        t.setTrainingDuration(Duration.ofMinutes(45));

        storage.create(t);

        Training fetched = storage.findById("T1");
        assertNotNull(fetched, "findById вернул null после create");
        assertEquals("Yoga", fetched.getTrainingName());
        assertEquals(Duration.ofMinutes(45), fetched.getTrainingDuration());
    }

    @Test
    void findById_whenNotExists_shouldReturnNull() {
        Training fetched = storage.findById("nonexistent");
        assertNull(fetched, "findById on a non-existent ID should return null");
    }

    @Test
    void findAll_shouldReturnAllStoredItems() {
        Training t1 = new Training();
        t1.setId("1");
        t1.setTrainingName("Pilates");

        Training t2 = new Training();
        t2.setId("2");
        t2.setTrainingName("Boxing");

        storage.create(t1);
        storage.create(t2);

        Collection<Training> all = storage.findAll();
        assertEquals(2, all.size());
        assertTrue(all.containsAll(List.of(t1, t2)));
    }

    @Test
    void update_shouldOverwriteExistingItem() {
        Training t = new Training();
        t.setId("U1");
        t.setTrainingName("CrossFit");
        storage.create(t);

        Training updated = new Training();
        updated.setId("U1");
        updated.setTrainingName("CrossFit Pro");
        updated.setTrainingDate(LocalDate.of(2025, 7, 1));
        updated.setTrainingDuration(Duration.ofMinutes(60));

        storage.update(updated);

        Training fetched = storage.findById("U1");
        assertNotNull(fetched);
        assertEquals("CrossFit Pro", fetched.getTrainingName());
        assertEquals(LocalDate.of(2025, 7, 1), fetched.getTrainingDate());
        assertEquals(Duration.ofMinutes(60), fetched.getTrainingDuration());
    }

    @Test
    void delete_shouldRemoveItem() {
        Training t = new Training();
        t.setId("D1");
        storage.create(t);

        assertNotNull(storage.findById("D1"), "The object must exist before deletion.");
        storage.delete("D1");
        assertNull(storage.findById("D1"), "After delete the object must be deleted");
    }
}

