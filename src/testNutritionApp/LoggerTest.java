package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

class LoggerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        // Redirect System.out to capture output
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        // Restore original System.out
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testSingletonInstance() {
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();
        assertSame(logger1, logger2, "Logger.getInstance() should return the same instance");
    }

    @Test
    void testGetInstanceNotNull() {
        Logger logger = Logger.getInstance();
        assertNotNull(logger, "Logger.getInstance() should not return null");
    }

    @Test
    void testLogFood() {
        Logger logger = Logger.getInstance();

        // Create test stub for Member
        TestMember member = new TestMember("John Doe");

        // Create a Food object
        Food food = new Food(250, 30, 20, 10, new ArrayList<>(), 1.5);

        // Call logFood
        logger.logFood(member, food);

        // Verify that addFoodLog was called with the correct food
        assertTrue(member.isAddFoodLogCalled(), "addFoodLog should be called");
        assertEquals(food, member.getLoggedFood(), "addFoodLog should be called with the correct Food object");

        // Check the console output
        String expectedOutput = "Food log added for member: John Doe" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "The console output should match expected message");
    }

    @Test
    void testLogWorkout() {
        Logger logger = Logger.getInstance();

        // Create test stub for Member
        TestMember member = new TestMember("Jane Smith");

        // Create a Workout list
        ArrayList<Workout> workouts = new ArrayList<>();
        workouts.add(new Workout("Pushups", 3, 15, 0, 30));
        workouts.add(new Workout("Squats", 4, 20, 0, 40));

        // Call logWorkout
        logger.logWorkout(member, workouts);

        // Verify that addWorkoutLog was called with the correct workouts
        assertTrue(member.isAddWorkoutLogCalled(), "addWorkoutLog should be called");
        assertEquals(workouts, member.getLoggedWorkouts(), "addWorkoutLog should be called with the correct workouts list");

        // Check the console output
        String expectedOutput = "Workout log added for member: Jane Smith" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString(), "The console output should match expected message");
    }

    /**
     * A simple test stub for Member to track calls to addFoodLog and addWorkoutLog.
     */
    private static class TestMember extends Member {
        private boolean addFoodLogCalled = false;
        private Food loggedFood;
        private boolean addWorkoutLogCalled = false;
        private ArrayList<Workout> loggedWorkouts;

        public TestMember(String name) {
            // We pass arbitrary values for id and password as they are not used in this test
            super(name, 1, "testPass");
        }

        @Override
        public void addFoodLog(Food food) {
            this.addFoodLogCalled = true;
            this.loggedFood = food;
        }

        @Override
        public void addWorkoutLog(ArrayList<Workout> workouts) {
            this.addWorkoutLogCalled = true;
            this.loggedWorkouts = workouts;
        }

        public boolean isAddFoodLogCalled() {
            return addFoodLogCalled;
        }

        public Food getLoggedFood() {
            return loggedFood;
        }

        public boolean isAddWorkoutLogCalled() {
            return addWorkoutLogCalled;
        }

        public ArrayList<Workout> getLoggedWorkouts() {
            return loggedWorkouts;
        }
    }
}