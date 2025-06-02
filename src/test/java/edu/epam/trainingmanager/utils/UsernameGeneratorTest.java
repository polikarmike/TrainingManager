package edu.epam.trainingmanager.utils;

import edu.epam.trainingmanager.utils.generation.UsernameGenerator;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

class UsernameGeneratorTest {

    private final UsernameGenerator generator = new UsernameGenerator();

    @Test
    void generate_WhenUsernameDoesNotExist_ShouldReturnBaseUsername() {
        Predicate<String> existsChecker = username -> false;
        String username = generator.generate("John", "Doe", existsChecker);
        assertEquals("John.Doe", username);
    }

    @Test
    void generate_WhenUsernameExistsOnce_ShouldAppendCounterOne() {
        Predicate<String> existsChecker = new Predicate<>() {
            private int callCount = 0;
            @Override
            public boolean test(String username) {
                callCount++;
                return "John.Doe".equals(username) && callCount == 1;
            }
        };

        String username = generator.generate("John", "Doe", existsChecker);
        assertEquals("John.Doe1", username);
    }

    @Test
    void generate_WhenUsernameExistsMultipleTimes_ShouldIncrementUntilFree() {
        Set<String> taken = Set.of("john.doe", "john.doe1", "john.doe2");
        Predicate<String> existsChecker = taken::contains;

        String username = generator.generate(" john ", " doe ", existsChecker);
        assertEquals("john.doe3", username);
    }

    @Test
    void generate_WithWhitespaceInNames_ShouldTrimAndRemoveAllInternalSpaces() {
        Predicate<String> existsChecker = username -> false;
        String username = generator.generate("  Mi chael  ", "  Jo hn  ", existsChecker);
        assertEquals("Michael.John", username);
    }

    @Test
    void generate_WhenLastNameEmpty_ShouldProduceFirstNameWithDotOnly() {
        Predicate<String> existsChecker = username -> false;
        String username = generator.generate("Alice", "   ", existsChecker);
        assertEquals("Alice.", username);
    }

    @Test
    void generate_WhenFirstNameEmpty_ShouldProduceDotAndLastNameOnly() {
        Predicate<String> existsChecker = username -> false;
        String username = generator.generate("   ", "Smith", existsChecker);
        assertEquals(".Smith", username);
    }
}

