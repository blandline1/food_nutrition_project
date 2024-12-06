package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import nutritionApp.NonApprovedPlan;
import nutritionApp.PlanType;

class NonApprovedPlanTest {

    private NonApprovedPlan nonApprovedPlan;

    @BeforeEach
    void setUp() {
        nonApprovedPlan = NonApprovedPlan.getInstance();
    }

    @Test
    void testSingletonInstance() {
        // Ensure that calling getInstance() always returns the same instance
        NonApprovedPlan anotherInstance = NonApprovedPlan.getInstance();
        
        // Assert that both instances are the same (singleton pattern)
        assertSame(nonApprovedPlan, anotherInstance, "NonApprovedPlan should return the same instance each time");
    }

    @Test
    void testPrintPlanType() {
        // Capture the printed output to verify the console output
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));
        
        // Call printPlanType, which should print to System.out
        nonApprovedPlan.printPlanType();

        // Verify that the output is as expected (trim any extra newline characters)
        String expectedOutput = "--- NON APPROVE PLAN ---";
        String actualOutput = outputStream.toString().replace("\r", "").trim();
        
        // Strip any trailing newline from the actual output
        assertEquals(expectedOutput, actualOutput);

        // Restore original System.out
        System.setOut(originalSystemOut);
    }

}
