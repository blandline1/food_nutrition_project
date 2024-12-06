package testNutritionApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nutritionApp.ExMathError;

/**
 * Test class for the ExMathError exception to achieve 100% coverage.
 */
class ExMathErrorTest {

    @BeforeEach
    void setUp() throws Exception {
        // Setup before each test if necessary
    }

    @AfterEach
    void tearDown() throws Exception {
        // Cleanup after each test if necessary
    }

    /**
     * Test the parameterless constructor of ExMathError.
     * It should set the default message "Math error, no negative number allowed."
     */
    @Test
    void testDefaultConstructor() {
        ExMathError exception = new ExMathError();
        assertEquals("Math error, no negative number allowed.", exception.getMessage(),
                "Default constructor should set message to 'Math error, no negative number allowed.'");
    }

    /**
     * Test the constructor of ExMathError that accepts a custom message.
     * It should set the message to the provided string.
     */
    @Test
    void testParameterizedConstructor() {
        String customMessage = "Custom math error message.";
        ExMathError exception = new ExMathError(customMessage);
        assertEquals(customMessage, exception.getMessage(),
                "Parameterized constructor should set message to the provided custom message.");
    }

    /**
     * Test that ExMathError can be thrown and caught with the default message.
     */
    @Test
    void testThrowDefaultConstructor() {
        try {
            throw new ExMathError();
        } catch (ExMathError e) {
            assertEquals("Math error, no negative number allowed.", e.getMessage(),
                    "Caught exception should have message 'Math error, no negative number allowed.'");
        }
    }

    /**
     * Test that ExMathError can be thrown and caught with a custom message.
     */
    @Test
    void testThrowParameterizedConstructor() {
        String customMessage = "Another custom math error message.";
        try {
            throw new ExMathError(customMessage);
        } catch (ExMathError e) {
            assertEquals(customMessage, e.getMessage(),
                    "Caught exception should have the custom message provided.");
        }
    }

    /**
     * Test the inheritance hierarchy of ExMathError.
     * It should be an instance of Exception.
     */
    @Test
    void testExceptionInheritance() {
        ExMathError exception = new ExMathError();
        assertTrue(exception instanceof Exception, "ExMathError should inherit from Exception.");
    }

    /**
     * Test that ExMathError is not null after instantiation.
     */
    @Test
    void testExceptionNotNull() {
        ExMathError exception1 = new ExMathError();
        ExMathError exception2 = new ExMathError("Test message");
        assertNotNull(exception1, "Exception instantiated with default constructor should not be null.");
        assertNotNull(exception2, "Exception instantiated with parameterized constructor should not be null.");
    }

    /**
     * Test the stack trace information of ExMathError.
     * Ensures that the stack trace is captured correctly.
     */
    @Test
    void testStackTrace() {
        try {
            throw new ExMathError("Stack trace test.");
        } catch (ExMathError e) {
            StackTraceElement[] stackTrace = e.getStackTrace();
            assertTrue(stackTrace.length > 0, "Stack trace should contain at least one element.");
            assertEquals("testStackTrace", stackTrace[0].getMethodName(),
                    "First element in stack trace should be 'testStackTrace'.");
        }
    }
}
