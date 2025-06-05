package edu.epam.training.manager.utils;

import edu.epam.training.manager.utils.generation.PasswordGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordGeneratorTest {
    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    private final PasswordGenerator passwordGenerator = new PasswordGenerator();

    @Test
    void testGeneratePasswordLength() {
        String password = passwordGenerator.generate();
        assertEquals(PASSWORD_LENGTH, password.length(),
                "Password length should be " + PASSWORD_LENGTH);
    }

    @Test
    void testGeneratePasswordCharacters() {
        String password = passwordGenerator.generate();
        for (char ch : password.toCharArray()) {
            assertTrue(ALLOWED_CHARACTERS.indexOf(ch) >= 0,
                    "Password contains invalid character: " + ch);
        }
    }

    @Test
    void testGenerateDifferentPasswords() {
        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            passwords.add(passwordGenerator.generate());
        }

        assertTrue(passwords.size() >= 90,
                "Generated passwords are not sufficiently random: only "
                        + passwords.size() + " unique values found out of 100 attempts.");
    }
}
