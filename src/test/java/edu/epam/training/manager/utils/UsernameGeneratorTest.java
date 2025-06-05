package edu.epam.training.manager.utils;

import edu.epam.training.manager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsernameGeneratorTest {

    private final UsernameGenerator generator = new UsernameGenerator();

    @Test
    void firstCandidate_ShouldBeBaseUsername() {
        String firstName = "John";
        String lastName = "Doe";
        Stream<String> candidates = generator.generateCandidates(firstName, lastName);
        String firstCandidate = candidates.findFirst().orElse("");
        assertEquals("John.Doe", firstCandidate);
    }

    @Test
    void generateCandidates_ShouldTrimAndRemoveExtraSpaces() {
        String firstName = "  Alice  ";
        String lastName = "  Smith   ";
        String candidate = generator.generateCandidates(firstName, lastName)
                .findFirst()
                .orElse("");
        assertEquals("Alice.Smith", candidate);
    }

    @Test
    void candidateSequence_ShouldIncrementSuffixCorrectly() {
        String firstName = "Bob";
        String lastName = "Marley";
        List<String> candidates = generator.generateCandidates(firstName, lastName)
                .limit(5)
                .toList();
        assertEquals("Bob.Marley", candidates.get(0));
        assertEquals("Bob.Marley1", candidates.get(1));
        assertEquals("Bob.Marley2", candidates.get(2));
        assertEquals("Bob.Marley3", candidates.get(3));
        assertEquals("Bob.Marley4", candidates.get(4));
    }

    @Test
    void multipleCandidates_AreGeneratedContinuously() {
        String firstName = "Carol";
        String lastName = "Danvers";
        List<String> candidates = generator.generateCandidates(firstName, lastName)
                .limit(3)
                .toList();
        assertEquals("Carol.Danvers", candidates.get(0));
        assertEquals("Carol.Danvers1", candidates.get(1));
        assertEquals("Carol.Danvers2", candidates.get(2));
    }
}
