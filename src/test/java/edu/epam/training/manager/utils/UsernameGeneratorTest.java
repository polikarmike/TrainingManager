package edu.epam.training.manager.utils;

import edu.epam.training.manager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsernameGeneratorTest {

    private final UsernameGenerator generator = new UsernameGenerator();

    @Test
    void generateBaseUsername_ShouldFormatAndConcatenateNames() {
        String firstName = "John";
        String lastName = "Doe";
        String baseUsername = generator.generateBaseUsername(firstName, lastName);
        assertEquals("john.doe", baseUsername);
    }

    @Test
    void generateBaseUsername_ShouldTrimAndRemoveExtraSpaces() {
        String firstName = "  Alice  ";
        String lastName = "  Smith   ";
        String baseUsername = generator.generateBaseUsername(firstName, lastName);
        assertEquals("alice.smith", baseUsername);
    }

    @Test
    void generateCandidates_ShouldIncrementSuffixCorrectly() {
        String baseUsername = "bob.marley";
        List<String> candidates = generator.generateCandidates(baseUsername, 5);
        assertEquals("bob.marley", candidates.get(0));
        assertEquals("bob.marley1", candidates.get(1));
        assertEquals("bob.marley2", candidates.get(2));
        assertEquals("bob.marley3", candidates.get(3));
        assertEquals("bob.marley4", candidates.get(4));
    }
}
