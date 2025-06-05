package edu.epam.training.manager.utils.generation;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHAR_SET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;
    private final SecureRandom random = new SecureRandom();

    public String generate() {
        if (PASSWORD_LENGTH <= 0) throw new IllegalArgumentException("Password length must be positive");

        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int index = random.nextInt(CHAR_SET.length());
            password.append(CHAR_SET.charAt(index));
        }
        return password.toString();
    }
}
