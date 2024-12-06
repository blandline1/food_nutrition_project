package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.Authenticator.UserType;
import nutritionApp.*;

class AuthenticatorTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private void resetAuthenticator() throws Exception {
        Authenticator auth = Authenticator.getInstance();
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(auth, null);

        Field usersField = Authenticator.class.getDeclaredField("users");
        usersField.setAccessible(true);
        ((List<?>) usersField.get(auth)).clear();

        Field idCountField = Authenticator.class.getDeclaredField("idCount");
        idCountField.setAccessible(true);
        idCountField.setInt(auth, 0);
    }

    @BeforeEach
    void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        resetAuthenticator();
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testGetInstanceAndLoggedUserInitiallyNull() {
        Authenticator auth = Authenticator.getInstance();
        assertSame(auth, Authenticator.getInstance(), "Should return the same Authenticator instance");
        assertNull(auth.getLoggedUser(), "Initially loggedUser should be null");
    }

    @Test
    void testLoginPageExit() {
        Authenticator auth = Authenticator.getInstance();
        String input = "-1\n"; // Exit immediately
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        boolean result = auth.LoginPage(scanner);
        scanner.close();
        assertTrue(result, "If choice is -1, should return true and exit");
    }

    @Test
    void testComplexScenarios() {
        Authenticator auth = Authenticator.getInstance();
        // This input sequence covers all major scenarios:
        // 9: Invalid choice
        // 1: Sign up member with invalid name (empty)
        // 1: Sign up member with valid name but invalid password (validName + empty password)
        // 1: Sign up member John pass (valid scenario)
        // 1: Sign up John again (username in use)
        // 2: Sign up trainer Toby tpass
        // 3: Login no user found (NoUser nopass)
        // 3: Login wrong password (John wrong)
        // 3: Login correct password (John pass) -> success, returns false
        // After success, call Logout and check getAllTrainers

        String input = 
            "9\n" +          // invalid choice
            "Dummy\n" +
            "DummyPass\n" +
            // invalid member sign up (empty username)
            "1\n" +
            "\n" +           // empty username => invalid input
            "pass\n" +
            // valid name but invalid password
            "1\n" +
            "ValidName\n" +
            "\n" +           // empty password => invalid input
            // valid member sign up (John pass)
            "1\n" +
            "John\n" +
            "pass\n" +
            // username already in use (John again)
            "1\n" +
            "John\n" +
            "anotherpass\n" +
            // sign up trainer
            "2\n" +
            "Toby\n" +
            "tpass\n" +
            // login no user found
            "3\n" +
            "NoUser\n" +
            "nopass\n" +
            // login wrong password
            "3\n" +
            "John\n" +
            "wrong\n" +
            // login correct password
            "3\n" +
            "John\n" +
            "pass\n";

        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        boolean result = auth.LoginPage(sc);
        sc.close();

        String output = outContent.toString();
        assertTrue(output.contains("Invalid choice. Please try again."), "Covers invalid choice");
        assertTrue(output.contains("Invalid username or password."), "Covers invalid inputs");
        assertTrue(output.contains("Successfully registered with name John as member"), "Member sign up");
        assertTrue(output.contains("Username already in use"), "Username already in use scenario");
        assertTrue(output.contains("Successfully registered with name Toby as trainer"), "Trainer sign up");
        assertTrue(output.contains("Invalid Credentials."), "Covers no user found and wrong password");
        assertTrue(output.contains("Welcome to the Food Nutrition App!"), "Successful login");
        assertFalse(result, "On successful login, should return false");

        // Check logout
        String logoutMsg = auth.Logout();
        assertTrue(logoutMsg.contains("Thank you for using Food Nutrition App!"));
        assertNull(auth.getLoggedUser(), "User should be null after logout");

        // Check getAllTrainers contains Toby
        List<Trainer> trainers = auth.getAllTrainers();
        assertEquals(1, trainers.size());
        assertEquals("Toby", trainers.get(0).getName());
    }

    @Test
    void testSignUpInvalidType() throws Exception {
        Authenticator auth = Authenticator.getInstance();
        Method m = Authenticator.class.getDeclaredMethod("SignUp", UserType.class, String.class, String.class);
        m.setAccessible(true);
        String result = (String) m.invoke(auth, new Object[]{null, "X", "Y"});
        assertEquals("Invalid Type", result, "Null userType should return 'Invalid Type'");
    }

    @Test
    void testHashPasswordAndIsValidInput() throws Exception {
        Authenticator auth = Authenticator.getInstance();
        Method hashMethod = Authenticator.class.getDeclaredMethod("HashPassword", String.class);
        hashMethod.setAccessible(true);
        String hashed = (String) hashMethod.invoke(auth, "abc");
        assertEquals("fgh", hashed, "Hashing 'abc' should produce 'fgh'");

        Method validMethod = Authenticator.class.getDeclaredMethod("isValidInput", String.class);
        validMethod.setAccessible(true);
        assertFalse((Boolean) validMethod.invoke(auth, (Object) null));
        assertFalse((Boolean) validMethod.invoke(auth, ""));
        assertFalse((Boolean) validMethod.invoke(auth, "   "));
        assertFalse((Boolean) validMethod.invoke(auth, "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz")); // 52 chars
        assertTrue((Boolean) validMethod.invoke(auth, "validName"));
    }
}
