package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nutritionApp.Food;

class FoodTest {

    private Food food1;
    private Food food2;
    private Food food3;
    private ArrayList<String> foodList;

    @BeforeEach
    void setUp() {
        // Initialize a food list for the tests
        foodList = new ArrayList<>(Arrays.asList("Apple", "Banana", "Orange"));
        
        // Create food objects with some example data
        food1 = new Food(250, 30, 20, 10, foodList, 2.5);
        food2 = new Food(250, 30, 20, 10, foodList, 2.5);
        food3 = new Food(300, 40, 25, 15, new ArrayList<>(Arrays.asList("Rice", "Chicken")), 3.0);
    }

    @Test
    void testEqualsNull() {
        // Test: Equals method with null object should return false
        assertFalse(food1.equals(null), "Food object should not be equal to null");
    }

    @Test
    void testEqualsDifferentClass() {
        // Test: Equals method with a different class (String) should return false
        String str = "Not a Food object";
        assertFalse(food1.equals(str), "Food object should not be equal to an object of a different class");
    }

    @Test
    void testEqualsSameValues() {
        // Test: Equals method when all fields match (food1 and food2 are identical)
        assertTrue(food1.equals(food2), "Food objects with the same values should be equal");
    }

    @Test
    void testEqualsDifferentCalories() {
        // Test: Equals method with different calories
        food3 = new Food(300, 30, 20, 10, foodList, 2.5);
        assertFalse(food1.equals(food3), "Food objects with different calories should not be equal");
    }

    @Test
    void testEqualsDifferentCarbs() {
        // Test: Equals method with different carbs
        food3 = new Food(250, 40, 20, 10, foodList, 2.5);
        assertFalse(food1.equals(food3), "Food objects with different carbs should not be equal");
    }

    @Test
    void testEqualsDifferentProteins() {
        // Test: Equals method with different proteins
        food3 = new Food(250, 30, 30, 10, foodList, 2.5);
        assertFalse(food1.equals(food3), "Food objects with different proteins should not be equal");
    }

    @Test
    void testEqualsDifferentFats() {
        // Test: Equals method with different fats
        food3 = new Food(250, 30, 20, 15, foodList, 2.5);
        assertFalse(food1.equals(food3), "Food objects with different fats should not be equal");
    }

    @Test
    void testEqualsDifferentFoodList() {
        // Test: Equals method with different foodList
        food3 = new Food(250, 30, 20, 10, new ArrayList<>(Arrays.asList("Bread", "Butter")), 2.5);
        assertFalse(food1.equals(food3), "Food objects with different food lists should not be equal");
    }

    @Test
    void testEqualsDifferentWaterIntake() {
        // Test: Equals method with different waterIntake
        food3 = new Food(250, 30, 20, 10, foodList, 3.0);
        assertFalse(food1.equals(food3), "Food objects with different water intake should not be equal");
    }

    @Test
    void testToString() {
        // Test: Ensure the toString method works as expected
        String expected = "Food [calories=250, carbs=30g, proteins=20g, fats=10g, foodList=[Apple, Banana, Orange], waterIntake=2.5L]";
        assertEquals(expected, food1.toString(), "The toString method should return the correct string representation of the Food object");
    }
    
    @Test
    void testGetCalories() {
        // Test: Ensure getCalories returns the correct value
        assertEquals(250, food1.getCalories(), "getCalories should return the correct number of calories");
    }

    @Test
    void testGetCarbs() {
        // Test: Ensure getCarbs returns the correct value
        assertEquals(30, food1.getCarbs(), "getCarbs should return the correct number of carbs in grams");
    }

    @Test
    void testGetProteins() {
        // Test: Ensure getProteins returns the correct value
        assertEquals(20, food1.getProteins(), "getProteins should return the correct number of proteins in grams");
    }

    @Test
    void testGetFats() {
        // Test: Ensure getFats returns the correct value
        assertEquals(10, food1.getFats(), "getFats should return the correct number of fats in grams");
    }

    @Test
    void testGetFoodList() {
        // Test: Ensure getFoodList returns the correct list of food items
        ArrayList<String> expectedFoodList = new ArrayList<>(Arrays.asList("Apple", "Banana", "Orange"));
        assertEquals(expectedFoodList, food1.getFoodList(), "getFoodList should return the correct list of food items");
    }

    @Test
    void testGetWaterIntake() {
        // Test: Ensure getWaterIntake returns the correct value
        assertEquals(2.5, food1.getWaterIntake(), "getWaterIntake should return the correct amount of water intake in liters");
    }
}
