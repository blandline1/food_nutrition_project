package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;

class MainTest {

    private Method runningProgramMethod;
    private Authenticator auth;

    @BeforeEach
    void setUp() throws Exception {
        // Access private static method runningProgram(Scanner) via reflection
        runningProgramMethod = Main.class.getDeclaredMethod("runningProgram", Scanner.class);
        runningProgramMethod.setAccessible(true);

        // Reset Authenticator
        auth = Authenticator.getInstance();
        auth.Logout();
    }

    @AfterEach
    void tearDown() throws Exception {
        auth.Logout();
    }

    @Test
    void testImmediateExitFromLogin() throws Exception {
        // User chooses "-1" at login menu to exit immediately
        String input = "-1\n";
        invokeRunningProgram(input);
        // Just ensures normal termination. Covers the exit condition at the start.
    }

    @Test
    void testSignUpLoginInvalidChoiceAndLogout() throws Exception {
        // Scenario:
        // 1) Sign up member (choice=1)
        // 2) name="m1", pass="p"
        // 3) Login with (choice=3) m1,p
        // After login: invalid choice (99), then -1 to logout
    	String input = 
    		    "1\nm1\np\n" + // Sign up member
    		    "3\nm1\np\n" + // Login
    		    "99\n" +        // Invalid choice in user menu
    		    "-1\n" +        // Logout (back to login)
    		    "-1\n";        // logout and exit
        invokeRunningProgram(input);
        // Covers sign up, login, invalid choice, logout branch.
    }


    // Helper method to invoke runningProgram(Scanner) via reflection
    private void invokeRunningProgram(String input) throws Exception {
        Scanner sc = new Scanner(new ByteArrayInputStream(input.getBytes()));
        runningProgramMethod.invoke(null, sc);
    }
}
