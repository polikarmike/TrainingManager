package edu.epam.trainingmanager.utils.generation;

import java.util.function.Predicate;

public class UsernameGenerator {
    private static final String DOT_SEPARATOR = ".";

    public String generate(String firstName, String lastName, Predicate<String> existsChecker) {
        String baseUsername = formatName(firstName) + DOT_SEPARATOR + formatName(lastName);
        String username = baseUsername;
        int counter = 1;

        while (existsChecker.test(username)) {
            username = baseUsername + counter;
            counter++;
        }

        return username;
    }

    private String formatName(String name) {
        return name.trim().replaceAll("\\s+", "");
    }
}