package edu.epam.trainingmanager.Storage;

import edu.epam.trainingmanager.domain.Trainee;
import edu.epam.trainingmanager.storage.impl.TraineeStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TraineeStorageTest {

    private TraineeStorage storage;

    @BeforeEach
    void setUp() {
        storage = new TraineeStorage();
    }

    @Test
    void create_and_findById_shouldStoreAndRetrieveTrainee() {
        Trainee t = new Trainee();
        t.setId("TR1");
        t.setFirstName("John");
        t.setLastName("Doe");
        t.setDateOfBirth(LocalDate.of(2000, 1, 1));
        t.setUsername("john.doe");

        storage.create(t);

        Trainee fetched = storage.findById("TR1");
        assertNotNull(fetched, "findById вернул null после create");
        assertEquals("John", fetched.getFirstName());
        assertEquals("john.doe", fetched.getUsername());
    }

    @Test
    void findById_whenNotExists_shouldReturnNull() {
        assertNull(storage.findById("nope"), "Некорректный ID должен вернуть null");
    }

    @Test
    void findAll_shouldReturnAllTrainees() {
        Trainee t1 = new Trainee();
        t1.setId("1");
        t1.setUsername("user1");

        Trainee t2 = new Trainee();
        t2.setId("2");
        t2.setUsername("user2");

        storage.create(t1);
        storage.create(t2);

        Collection<Trainee> all = storage.findAll();
        assertEquals(2, all.size());
        assertTrue(all.containsAll(List.of(t1, t2)));
    }

    @Test
    void update_shouldOverwriteExistingTrainee() {
        Trainee t = new Trainee();
        t.setId("U2");
        t.setUsername("original");
        storage.create(t);

        Trainee updated = new Trainee();
        updated.setId("U2");
        updated.setUsername("updated");
        updated.setFirstName("Alice");
        updated.setLastName("Green");
        storage.update(updated);

        Trainee fetched = storage.findById("U2");
        assertNotNull(fetched);
        assertEquals("updated", fetched.getUsername());
        assertEquals("Alice", fetched.getFirstName());
    }

    @Test
    void delete_shouldRemoveTrainee() {
        Trainee t = new Trainee();
        t.setId("DEL1");
        storage.create(t);

        assertNotNull(storage.findById("DEL1"), "Trainee must exist before deleting");
        storage.delete("DEL1");
        assertNull(storage.findById("DEL1"), "After deletion trainee should not exist");
    }

    @Test
    void findByUsername_whenExists_shouldReturnMatchingTrainee() {
        Trainee t1 = new Trainee();
        t1.setId("A1");
        t1.setUsername("alice123");

        Trainee t2 = new Trainee();
        t2.setId("B2");
        t2.setUsername("bob321");

        storage.create(t1);
        storage.create(t2);

        Trainee found = storage.findByUsername("bob321");
        assertNotNull(found);
        assertEquals("B2", found.getId());
        assertEquals("bob321", found.getUsername());
    }

    @Test
    void findByUsername_whenNotExists_shouldReturnNull() {
        assertNull(storage.findByUsername("ghost"), "If there is no login, the method should return null");
    }
}

