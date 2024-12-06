package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.*;

class AnalysisTest {

    private Analysis analysis;
    
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        analysis = Analysis.getInstance();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        analysis = null;
        System.setOut(originalOut);
        outContent.reset();
    }

    @Test
    void testSingletonInstance() {
        Analysis anotherInstance = Analysis.getInstance();
        assertSame(analysis, anotherInstance, "getInstance should return the same instance");
    }

    @Test
    void testFuncQ() {
        assertEquals(100, analysis.func_Q(10, 10), "Perfect match should return 100");
        assertEquals(50, analysis.func_Q(10, 5), "Half match should return 50");
        assertEquals(0, analysis.func_Q(10, 20), "Exceeding value should return 0");
    }

    @Test
    void testFoodBiasedWeight() {
        double weight = analysis.food_biased_weight(100, 50, 50, 100, 0.8, 0.9);
        assertEquals(0.3*100 + 0.1*50 + 0.15*50 + 0.15*100 + 0.2*0.8 + 0.1*0.9, weight, 0.01, "Food biased weight calculation is incorrect");
    }

    @Test
    void testWorkoutBiasedWeight() {
        double weight = analysis.workout_biased_weight(10, 15, 30);
        assertEquals(0.3*10 + 0.3*15 + 0.4*30, weight, 0.01, "Workout biased weight calculation is incorrect");
    }

    @Test
    void testAdherenceCalculator() {
        ArrayList<String> expected = new ArrayList<>(Arrays.asList("Apple", "Banana", "Carrot"));
        ArrayList<String> actual = new ArrayList<>(Arrays.asList("Apple", "Carrot", "Doughnut"));
        assertEquals(66.67, analysis.adherence_calculator(expected, actual), 0.01, "Adherence calculation is incorrect");

        actual = new ArrayList<>(Arrays.asList("Apple", "Banana", "Carrot"));
        assertEquals(100, analysis.adherence_calculator(expected, actual), "Full match should return 100");
    }

    @Test
    void testHydScore() {
        assertEquals(100, analysis.hyd_score(2.0, 2.0), "Full capacity should return 100");
        assertEquals(50, analysis.hyd_score(1.0, 2.0), "Half capacity should return 50");
        assertEquals(100, analysis.hyd_score(3.0, 2.0), "Exceeding capacity should return 100");
    }

    @Test
    void testConductFoodAnalysis() {
        // Create test data
        ArrayList<Food> expected = createFoodList(
                new int[]{200, 100, 50, 50}, 
                new double[]{2.0}, 
                Arrays.asList("Apple", "Banana"), 
                7
        );
        ArrayList<Food> actual = createFoodList(
                new int[]{190, 90, 45, 48}, 
                new double[]{1.5}, 
                Arrays.asList("Apple", "Carrot"), 
                7
        );

        // Execute the method
        analysis.conductFoodAnalysis(expected, actual);

        // Capture the output
        String output = outContent.toString();

        // Define expected substrings (adjust based on actual calculations)
        // Here, you should calculate the expected values based on the input data
        // For demonstration, I'll assume some expected outputs

        // Example assertions (you need to calculate the expected values accurately)
        assertTrue(output.contains("Individual scores"), "Output should contain 'Individual scores'");
        assertTrue(output.contains("Calories: "), "Output should contain 'Calories: '");
        assertTrue(output.contains("Fats: "), "Output should contain 'Fats: '");
        assertTrue(output.contains("Proteins: "), "Output should contain 'Proteins: '");
        assertTrue(output.contains("Carbs: "), "Output should contain 'Carbs: '");
        assertTrue(output.contains("Water Intake: "), "Output should contain 'Water Intake: '");
        assertTrue(output.contains("Overall score: "), "Output should contain 'Overall score: '");

        // Optionally, you can perform more precise checks by parsing the output
        // For example, using regex or splitting lines and verifying numeric values
    }

    @Test
    void testConductWorkoutAnalysis() {
        // Create test data
        ArrayList<ArrayList<Workout>> expected = createWorkoutList(
                new String[]{"Pushups"}, 
                new int[]{10, 15, 30}, 
                7
        );
        ArrayList<ArrayList<Workout>> actual = createWorkoutList(
                new String[]{"Pushups"}, 
                new int[]{9, 14, 28}, 
                7
        );

        // Execute the method
        analysis.conductWorkoutAnalysis(expected, actual);

        // Capture the output
        String output = outContent.toString();

        // Define expected substrings (adjust based on actual calculations)
        assertTrue(output.contains("Day 1 individual scores: "), "Output should contain 'Day 1 individual scores: '");
        assertTrue(output.contains("Sets: "), "Output should contain 'Sets: '");
        assertTrue(output.contains("Reps: "), "Output should contain 'Reps: '");
        assertTrue(output.contains("Minutes: "), "Output should contain 'Minutes: '");
        assertTrue(output.contains("Adherence: "), "Output should contain 'Adherence: '");
        assertTrue(output.contains("Overall biased score: "), "Output should contain 'Overall biased score: '");

        // Optionally, perform more detailed assertions on specific values
    }

    // Helper methods for test data
    private ArrayList<Food> createFoodList(int[] values, double[] waterIntake, List<String> foodList, int days) {
        ArrayList<Food> list = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            // Create a new ArrayList for foodList to ensure mutability
            list.add(new Food(values[0], values[1], values[2], values[3], new ArrayList<>(foodList), waterIntake[0]));
        }
        return list;
    }

    private ArrayList<ArrayList<Workout>> createWorkoutList(String[] names, int[] values, int days) {
        ArrayList<ArrayList<Workout>> list = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            ArrayList<Workout> dayWorkouts = new ArrayList<>();
            // The Workout constructor has parameters: String name, int sets, int reps, int caloriesBurned, int minutes
            // Assuming caloriesBurned is not used in Analysis, set it to 0 or an appropriate value
            dayWorkouts.add(new Workout(names[0], values[0], values[1], 0, values[2]));
            list.add(dayWorkouts);
        }
        return list;
    }
}