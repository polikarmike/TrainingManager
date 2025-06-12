package edu.epam.training.manager.utils.generation;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
public class UsernameGenerator {
    private static final String DOT_SEPARATOR = ".";

    public String generateBaseUsername(String firstName, String lastName) {
        return format(firstName) + DOT_SEPARATOR + format(lastName);
    }

    public List<String> generateCandidates(String baseUsername, int limit) {
        return Stream.iterate(0, i -> i + 1)
                .map(i -> i == 0 ? baseUsername : baseUsername + i)
                .limit(limit)
                .toList();
    }

    private String format(String name) {
        return name.trim().toLowerCase().replaceAll("\\s+", "");
    }
}
