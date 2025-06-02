package edu.epam.training.manager.utils.generation;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class UsernameGenerator {
    private static final String DOT_SEPARATOR = ".";

    public Stream<String> generateCandidates(String firstName, String lastName) {
        String baseUsername = formatName(firstName) + DOT_SEPARATOR + formatName(lastName);

        return Stream.iterate(0, i -> i + 1)
                .map(i -> i == 0 ? baseUsername : baseUsername + i);
    }

    private String formatName(String name) {
        return name.trim().replaceAll("\\s+", "");
    }
}