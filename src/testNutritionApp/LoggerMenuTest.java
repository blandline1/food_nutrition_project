package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import nutritionApp.*;

class LoggerMenuTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Minimal test member stub
    static class TestMember extends Member {
        private boolean logsShown = false;
        public TestMember(String name) {
            super(name, 1, "testPass");
        }

        @Override
        public void showLogs() {
            logsShown = true;
            System.out.println("Logs displayed for member: " + getName());
        }

        @Override
        public void addFoodLog(Food food) {
            System.out.println("addFoodLog called with: " + food);
        }

        @Override
        public void addWorkoutLog(ArrayList<Workout> workouts) {
            System.out.println("addWorkoutLog called with: " + workouts);
        }

        public boolean isLogsShown() {
            return logsShown;
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));

        // Use reflection to set loggedUser in Authenticator
        TestMember testMember = new TestMember("John Doe");
        Field loggedUserField = Authenticator.class.getDeclaredField("loggedUser");
        loggedUserField.setAccessible(true);
        loggedUserField.set(Authenticator.getInstance(), testMember);
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testGetInstance() {
        LoggerMenu menu1 = LoggerMenu.getInstance();
        LoggerMenu menu2 = LoggerMenu.getInstance();
        assertSame(menu1, menu2, "LoggerMenu.getInstance() should return the same instance");
    }

    @Test
    void testShowLoggerMenuAllChoices() {
        // Input to cover all menu options:
        // 1) Log Food (enter: 100 cal, 20 carbs, 10 proteins, 5 fats, "Apple,Banana", water=2.0)
        // 2) Log Workout (1 workout: "Pushups", sets=3, reps=15, calBurnt=100, minutes=30)
        // 3) Show Logs
        // 9) Invalid choice
        // 4) Exit
        String input =
            "1\n" +         // Log Food
            "100\n20\n10\n5\nApple,Banana\n2.0\n" +
            "2\n" +          // Log Workout
            "1\n" +          // number_of_workouts=1
            "Pushups\n" +
            "3\n" +          // sets
            "15\n" +         // reps
            "100\n" +        // calories burned
            "30\n" +         // minutes
            "3\n" +          // Show Logs
            "9\n" +          // Invalid choice
            "4\n";           // Exit Logger menu

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LoggerMenu menu = LoggerMenu.getInstance();
        menu.showLoggerMenu(scanner);

        scanner.close();

        String output = outContent.toString();

        // Validate menu prompts and actions
        assertTrue(output.contains("Logger Menu:"), "Menu should be displayed");
        assertTrue(output.contains("Food log added for member: John Doe"), "Food logging message expected");
        assertTrue(output.contains("Workout log added for member: John Doe"), "Workout logging message expected");
        assertTrue(output.contains("Logs displayed for member: John Doe"), "Logs should be displayed for member");
        assertTrue(output.contains("Invalid choice. Please try again."), "Invalid choice message expected");
    }

    @Test
    void testLogFoodMethodDirectly() {
        String input =
            "300\n" +       // calories
            "50\n" +        // carbs
            "25\n" +        // proteins
            "10\n" +        // fats
            "Orange,Yogurt\n" + // food items
            "1.5\n";        // water

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LoggerMenu menu = LoggerMenu.getInstance();
        Food food = menu.logFood(scanner);

        assertEquals(300, food.getCalories());
        assertEquals(50, food.getCarbs());
        assertEquals(25, food.getProteins());
        assertEquals(10, food.getFats());
        assertEquals(2, food.getFoodList().size());
        assertEquals("Orange", food.getFoodList().get(0).trim());
        assertEquals("Yogurt", food.getFoodList().get(1).trim());
        assertEquals(1.5, food.getWaterIntake(), 0.0001);

        scanner.close();
    }

    @Test
    void testGetDailyWorkoutMethodDirectly() {
        String input =
            "1\n" +       // num_workouts=1
            "Burpees\n" +
            "5\n" +        // sets
            "10\n" +       // reps
            "200\n" +      // calories burned
            "15\n";        // minutes

        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LoggerMenu menu = LoggerMenu.getInstance();
        ArrayList<Workout> workouts = menu.getDailyWorkout(scanner);
        scanner.close();

        assertEquals(1, workouts.size());
        Workout w = workouts.get(0);
        assertEquals("Burpees", w.getName());
        assertEquals(5, w.getSets());
        assertEquals(10, w.getReps());
        // According to the LoggerMenu code, it prompts "Enter number of calories burned:" twice by mistake.
        // The second prompt is actually used as minutes. We trust the code's reading order.
        assertEquals(15, w.getMinutes());
    }

}
