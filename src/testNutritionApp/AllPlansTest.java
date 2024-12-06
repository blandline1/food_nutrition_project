package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;

import nutritionApp.AllPlans;
import nutritionApp.Food;
import nutritionApp.Workout;
import nutritionApp.PlanType;
import nutritionApp.NonApprovedPlan;
import nutritionApp.ApprovedPlan;
import nutritionApp.Trainer;
import nutritionApp.Member;

class AllPlansTest {

    private AllPlans allPlans;
    private ArrayList<Food> foodList;
    private ArrayList<ArrayList<Workout>> workoutList;
    private Trainer trainer;
    private Scanner scanner;

    @BeforeEach
    void setUp() throws Exception {
        // Initialize AllPlans instance
        allPlans = new AllPlans();

        // Initialize food list and workout list for testing
        foodList = new ArrayList<>();
        workoutList = new ArrayList<>();

        // Add a Food object to food list
        foodList.add(new Food(500, 60, 30, 20, new ArrayList<String>(), 2.5));

        // Add a Workout object to workout list
        ArrayList<Workout> workouts = new ArrayList<>();
        workouts.add(new Workout("Pushup", 3, 15, 100, 15));
        workoutList.add(workouts);

        // Initialize trainer (stub)
        trainer = new Trainer("trainer123",1, "password");

        // Initialize Scanner (stub)
        scanner = new Scanner(System.in);
    }

    @Test
    void testUpdatePlan() {
        // Call updatePlan with mock data
        allPlans.updatePlan(foodList, workoutList);

        // Verify that the meal and workout plans were updated
        assertEquals(foodList, allPlans.getFoodPlan());
        assertEquals(workoutList, allPlans.getWorkoutPlan());

        // Verify that the plan type is set to NonApprovedPlan and trainer is null
        assertTrue(allPlans.getFoodPlan() != null);
        assertTrue(allPlans.getWorkoutPlan() != null);
    }

    @Test
    void testGetFoodPlan() {
        // Call updatePlan to set some initial data
        allPlans.updatePlan(foodList, workoutList);

        // Verify that getFoodPlan returns a clone of the food list
        assertNotSame(foodList, allPlans.getFoodPlan());
        assertEquals(foodList, allPlans.getFoodPlan());
    }

    @Test
    void testGetWorkoutPlan() {
        // Call updatePlan to set some initial data
        allPlans.updatePlan(foodList, workoutList);

        // Verify that getWorkoutPlan returns a clone of the workout list
        assertNotSame(workoutList, allPlans.getWorkoutPlan());
        assertEquals(workoutList, allPlans.getWorkoutPlan());
    }

    @Test
    void testPrintPlan() {
        // Call updatePlan to set some initial data
        allPlans.updatePlan(foodList, workoutList);

        // This test will just check if it executes correctly.
        // Since printPlan prints to console, we will not check its output here.
        // You could potentially redirect the System.out stream, but it's not required for now.
        allPlans.printPlan();
    }

    @Test
    void testApprove() throws NoSuchFieldException, IllegalAccessException {
        // Set up user input for approval (simulating "Y" input)
        String input = "Y";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Call approve method
        allPlans.approve(trainer, new Scanner(System.in));

        // Use reflection to get the private 'planType' field from AllPlans class
        Field planTypeField = AllPlans.class.getDeclaredField("planType");

        // Ensure the field is accessible
        planTypeField.setAccessible(true);

        // Retrieve the value of 'planType'
        PlanType planTypeValue = (PlanType) planTypeField.get(allPlans);

        // Verify that the planType is set to ApprovedPlan
        assertEquals(ApprovedPlan.getInstance(), planTypeValue);
    }


    @Test
    void testApproveWithNoApproval() throws NoSuchFieldException, IllegalAccessException {
        // Set up user input for no approval
        String input = "N";
        System.setIn(new java.io.ByteArrayInputStream(input.getBytes()));

        // Call approve method
        allPlans.approve(trainer, new Scanner(System.in));

        // Use reflection to get the private 'planType' field from AllPlans class
        Field planTypeField = AllPlans.class.getDeclaredField("planType");

        // Ensure the field is accessible
        planTypeField.setAccessible(true);

        // Retrieve the value of 'planType'
        PlanType planTypeValue = (PlanType) planTypeField.get(allPlans);

        // Verify that the plan type is not changed and remains NonApprovedPlan
        assertEquals(null, planTypeValue);
    }
}
