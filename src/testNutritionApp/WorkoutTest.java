package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nutritionApp.Workout;

class WorkoutTest {

    private Workout workout;

    @BeforeEach
    void setUp() {
        // Initialize a Workout object before each test
        workout = new Workout("Push-ups", 3, 12, 10, 15); // Example workout
    }

    @Test
    void testConstructor() {
        // Verify that the Workout object is initialized correctly
        assertEquals("Push-ups", workout.getName());
        assertEquals(3, workout.getSets());
        assertEquals(12, workout.getReps());
        assertEquals(15, workout.getMinutes());
    }

    @Test
    void testGetters() {
        // Test each getter to make sure they return the correct values
        assertEquals("Push-ups", workout.getName());
        assertEquals(3, workout.getSets());
        assertEquals(12, workout.getReps());
        assertEquals(15, workout.getMinutes());
    }

    @Test
    void testToString() {
        // Verify that the toString() method returns the expected string representation
        String expectedString = "Workout [name=Push-ups, sets=3, reps=12, minutes=15]";
        assertEquals(expectedString, workout.toString());
    }

    @Test
    void testEqualsSameValues() {
        // Create another Workout object with the same values
        Workout anotherWorkout = new Workout("Push-ups", 3, 12, 10, 15);
        
        // Verify that two workouts with the same values are considered equal
        assertTrue(workout.equals(anotherWorkout), "Workouts with the same values should be equal");
    }

    @Test
    void testEqualsDifferentValues() {
        // Create a different Workout object
        Workout differentWorkout = new Workout("Sit-ups", 3, 12, 10, 15);
        
        // Verify that two workouts with different values are not equal
        assertFalse(workout.equals(differentWorkout), "Workouts with different values should not be equal");
    }

    @Test
    void testEqualsNull() {
        // Verify that a Workout object is not equal to null
        assertFalse(workout.equals(null), "Workout should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        // Verify that a Workout object is not equal to an object of a different class
        String differentClassObject = "Not a Workout";
        assertFalse(workout.equals(differentClassObject), "Workout should not be equal to an object of a different class");
    }
    
    @Test
    void testEqualsDifferentName() {
        // Create a different Workout object with the same sets, reps, and minutes but different name
        Workout differentWorkout = new Workout("Sit-ups", 3, 12, 10, 15);
        
        // Test case where names are different
        assertFalse(workout.equals(differentWorkout), "Workouts with different names should not be equal");
    }

    @Test
    void testEqualsDifferentSets() {
        // Create a different Workout object with the same name, reps, and minutes but different sets
        Workout differentWorkout = new Workout("Push-ups", 4, 12, 10, 15);
        
        // Test case where sets are different
        assertFalse(workout.equals(differentWorkout), "Workouts with different sets should not be equal");
    }

    @Test
    void testEqualsDifferentReps() {
        // Create a different Workout object with the same name, sets, and minutes but different reps
        Workout differentWorkout = new Workout("Push-ups", 3, 10, 10, 15);
        
        // Test case where reps are different
        assertFalse(workout.equals(differentWorkout), "Workouts with different reps should not be equal");
    }

    @Test
    void testEqualsDifferentMinutes() {
        // Create a different Workout object with the same name, sets, and reps but different minutes
        Workout differentWorkout = new Workout("Push-ups", 3, 12, 10, 20);
        
        // Test case where minutes are different
        assertFalse(workout.equals(differentWorkout), "Workouts with different minutes should not be equal");
    }
    
}
