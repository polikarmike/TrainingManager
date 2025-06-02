package edu.epam.trainingmanager.utils;

import edu.epam.trainingmanager.utils.generation.IdGenerator;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class IdGeneratorTest {

    private final IdGenerator generator = new IdGenerator();
    private static final Pattern UUID_PATTERN = Pattern.compile(
            "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89ABab][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$"
    );

    @Test
    void generate_ShouldReturnValidUUIDString() {
        String id = generator.generate();
        assertNotNull(id);
        assertTrue(UUID_PATTERN.matcher(id).matches(),
                "The generated string does not conform to the UUID format: " + id);
        assertDoesNotThrow(() -> UUID.fromString(id));
    }

    @Test
    void generate_TwoCallsShouldReturnDifferentValues() {
        String id1 = generator.generate();
        String id2 = generator.generate();
        assertNotNull(id1);
        assertNotNull(id2);
        assertNotEquals(id1, id2, "Two calls must not return the same UUID.");
    }
}
