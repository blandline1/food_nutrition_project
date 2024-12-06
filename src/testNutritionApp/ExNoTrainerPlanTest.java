package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.ExNoTrainerPlan;

/**
 * Test class for the ExNoTrainerPlan exception to achieve 100% coverage.
 */
class ExNoTrainerPlanTest {

    @BeforeEach
    void setUp() throws Exception {
        // Setup before each test if necessary
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup after each test if necessary
    }

    /**
     * Test the parameterless constructor of ExNoTrainerPlan.
     * It should set the default message "No existing plan for trainer available".
     */
    @Test
    void testDefaultConstructor() {
        ExNoTrainerPlan exception = new ExNoTrainerPlan();
        assertEquals("No existing plan for trainer available", exception.getMessage(),
                "Default constructor should set message to 'No existing plan for trainer available'");
    }

    /**
     * Test the constructor of ExNoTrainerPlan that accepts a custom message.
     * It should set the message to the provided string.
     */
    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom no trainer plan message.";
        ExNoTrainerPlan exception = new ExNoTrainerPlan(customMessage);
        assertEquals(customMessage, exception.getMessage(),
                "Parameterized constructor should set message to the provided custom message.");
    }

    /**
     * Test that ExNoTrainerPlan can be thrown and caught with the default message.
     */
    @Test
    void testThrowDefaultConstructor() {
        try {
            throw new ExNoTrainerPlan();
        } catch (ExNoTrainerPlan e) {
            assertEquals("No existing plan for trainer available", e.getMessage(),
                    "Caught exception should have message 'No existing plan for trainer available'");
        }
    }

    /**
     * Test that ExNoTrainerPlan can be thrown and caught with a custom message.
     */
    @Test
    void testThrowParameterizedConstructor() {
        String customMessage = "Another custom no trainer plan message.";
        try {
            throw new ExNoTrainerPlan(customMessage);
        } catch (ExNoTrainerPlan e) {
            assertEquals(customMessage, e.getMessage(),
                    "Caught exception should have the custom message provided.");
        }
    }

    /**
     * Test the inheritance hierarchy of ExNoTrainerPlan.
     * It should be an instance of Exception.
     */
    @Test
    void testExceptionInheritance() {
        ExNoTrainerPlan exception = new ExNoTrainerPlan();
        assertTrue(exception instanceof Exception, "ExNoTrainerPlan should inherit from Exception.");
    }

    /**
     * Test that ExNoTrainerPlan is not null after instantiation.
     */
    @Test
    void testExceptionNotNull() {
        ExNoTrainerPlan exception1 = new ExNoTrainerPlan();
        ExNoTrainerPlan exception2 = new ExNoTrainerPlan("Test message");
        assertNotNull(exception1, "Exception instantiated with default constructor should not be null.");
        assertNotNull(exception2, "Exception instantiated with parameterized constructor should not be null.");
    }

    /**
     * Test the stack trace information of ExNoTrainerPlan.
     * Ensures that the stack trace is captured correctly.
     */
    @Test
    void testStackTrace() {
        try {
            throw new ExNoTrainerPlan("Stack trace test.");
        } catch (ExNoTrainerPlan e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            assertTrue(stackTrace.length > 0, "Stack trace should contain at least one element.");
            assertEquals("testStackTrace", stackTrace[0].getMethodName(),
                    "First element in stack trace should be 'testStackTrace'.");
        }
    }
}
