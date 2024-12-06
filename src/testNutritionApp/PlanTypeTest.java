package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nutritionApp.ApprovedPlan;
import nutritionApp.NonApprovedPlan;
import nutritionApp.PlanType;

class PlanTypeTest {

    private PlanType approvedPlan;
    private PlanType nonApprovedPlan;

    @BeforeEach
    void setUp() {
        // Instantiate the concrete implementations of PlanType
        approvedPlan = ApprovedPlan.getInstance();
        nonApprovedPlan = NonApprovedPlan.getInstance();
    }

    @Test
    void testApprovedPlanPrint() {
        // Capture the printed output to verify the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call printPlanType on the ApprovedPlan instance
        approvedPlan.printPlanType();

        // Verify that the output matches the expected output
        String expectedOutput = "--- APPROVE PLAN ---";
        String actualOutput = outputStream.toString().replace("\r", "").trim(); // Remove any extra newline or carriage return

        assertEquals(expectedOutput, actualOutput, "ApprovedPlan should print the correct plan type.");

        // Restore original System.out
        System.setOut(originalSystemOut);
    }

    @Test
    void testNonApprovedPlanPrint() {
        // Capture the printed output to verify the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Call printPlanType on the NonApprovedPlan instance
        nonApprovedPlan.printPlanType();

        // Verify that the output matches the expected output
        String expectedOutput = "--- NON APPROVE PLAN ---";
        String actualOutput = outputStream.toString().replace("\r", "").trim(); // Remove any extra newline or carriage return

        assertEquals(expectedOutput, actualOutput, "NonApprovedPlan should print the correct plan type.");

        // Restore original System.out
        System.setOut(originalSystemOut);
    }
}
