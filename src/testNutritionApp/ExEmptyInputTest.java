package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.ExEmptyInput;

/**
 * Test class for the ExEmptyInput exception to achieve 100% coverage.
 */
class ExEmptyInputTest {

    @BeforeEach
    void setUp() throws Exception {
        // Setup before each test if necessary
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup after each test if necessary
    }

    /**
     * Test the parameterless constructor of ExEmptyInput.
     * It should set the default message "Empty input."
     */
    @Test
    void testDefaultConstructor() {
        ExEmptyInput exception = new ExEmptyInput();
        assertEquals("Empty input.", exception.getMessage(),
                "Default constructor should set message to 'Empty input.'");
    }

    /**
     * Test the constructor of ExEmptyInput that accepts a custom message.
     * It should set the message to the provided string.
     */
    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom empty input message.";
        ExEmptyInput exception = new ExEmptyInput(customMessage);
        assertEquals(customMessage, exception.getMessage(),
                "Parameterized constructor should set message to the provided custom message.");
    }

    /**
     * Test that ExEmptyInput can be thrown and caught with the default message.
     */
    @Test
    void testThrowDefaultConstructor() {
        try {
            throw new ExEmptyInput();
        } catch (ExEmptyInput e) {
            assertEquals("Empty input.", e.getMessage(),
                    "Caught exception should have message 'Empty input.'");
        }
    }

    /**
     * Test that ExEmptyInput can be thrown and caught with a custom message.
     */
    @Test
    void testThrowParameterizedConstructor() {
        String customMessage = "Another custom message.";
        try {
            throw new ExEmptyInput(customMessage);
        } catch (ExEmptyInput e) {
            assertEquals(customMessage, e.getMessage(),
                    "Caught exception should have the custom message provided.");
        }
    }

    /**
     * Test the inheritance hierarchy of ExEmptyInput.
     * It should be an instance of Exception.
     */
    @Test
    void testExceptionInheritance() {
        ExEmptyInput exception = new ExEmptyInput();
        assertTrue(exception instanceof Exception, "ExEmptyInput should inherit from Exception.");
    }

    /**
     * Test that ExEmptyInput is not null after instantiation.
     */
    @Test
    void testExceptionNotNull() {
        ExEmptyInput exception1 = new ExEmptyInput();
        ExEmptyInput exception2 = new ExEmptyInput("Test message");
        assertNotNull(exception1, "Exception instantiated with default constructor should not be null.");
        assertNotNull(exception2, "Exception instantiated with parameterized constructor should not be null.");
    }

    /**
     * Test the stack trace information of ExEmptyInput.
     * Ensures that the stack trace is captured correctly.
     */
    @Test
    void testStackTrace() {
        try {
            throw new ExEmptyInput("Stack trace test.");
        } catch (ExEmptyInput e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            assertTrue(stackTrace.length > 0, "Stack trace should contain at least one element.");
            assertEquals("testStackTrace", stackTrace[0].getMethodName(),
                    "First element in stack trace should be 'testStackTrace'.");
        }
    }
}
