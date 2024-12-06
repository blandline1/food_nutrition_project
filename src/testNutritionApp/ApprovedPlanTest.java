package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nutritionApp.ApprovedPlan;
import nutritionApp.PlanType;

class ApprovedPlanTest {

    private ApprovedPlan approvedPlan;

    @BeforeEach
    void setUp() {
        // Get the singleton instance of ApprovedPlan
        approvedPlan = ApprovedPlan.getInstance();
    }

    @Test
    void testSingletonInstance() {
        // Ensure that calling getInstance() always returns the same instance
        ApprovedPlan anotherInstance = ApprovedPlan.getInstance();
        
        // Assert that both instances are the same (singleton pattern)
        assertSame(approvedPlan, anotherInstance, "ApprovedPlan should return the same instance each time");
    }

    @Test
    void testPrintPlanType() {
        // Capture the printed output to verify the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // Call printPlanType, which should print to System.out
        approvedPlan.printPlanType();

        // Verify that the output is as expected
        String expectedOutput = "--- APPROVE PLAN ---";
        String actualOutput = outputStream.toString().replace("\r", "").trim();
        
        // Ensure that the output matches the expected output
        assertEquals(expectedOutput, actualOutput, "The printed output should match the expected output.");

        // Restore original System.out
        System.setOut(originalSystemOut);
    }
}
