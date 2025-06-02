//package edu.epam.training.manager.utils;
//
//import edu.epam.training.manager.utils.generation.PasswordGenerator;
//import org.junit.jupiter.api.Test;
//
//import java.util.regex.Pattern;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class PasswordGeneratorTest {
//
//    private final PasswordGenerator generator = new PasswordGenerator();
//
//    @Test
//    void generate_WithPositiveLength_ShouldReturnStringOfCorrectLength() {
//        int length = 12;
//        String pwd = generator.generate(length);
//        assertNotNull(pwd);
//        assertEquals(length, pwd.length());
//    }
//
//    @Test
//    void generate_ShouldContainOnlyAllowedCharacters() {
//        int length = 50;
//        String pwd = generator.generate(length);
//        Pattern allowed = Pattern.compile("^[A-Za-z0-9]{" + length + "}$");
//        assertTrue(allowed.matcher(pwd).matches(),
//                "The password contains invalid characters: " + pwd);
//    }
//
//    @Test
//    void generate_MultipleCallsShouldProduceDifferentPasswordsMostLikely() {
//        String p1 = generator.generate(16);
//        String p2 = generator.generate(16);
//        assertNotEquals(p1, p2,
//                "Two consecutive generators produced the same password");
//    }
//
//    @Test
//    void generate_WithZeroOrNegativeLength_ShouldThrowException() {
//        assertThrows(IllegalArgumentException.class, () -> generator.generate(0));
//        assertThrows(IllegalArgumentException.class, () -> generator.generate(-5));
//    }
//}
